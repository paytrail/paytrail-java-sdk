package io.paytrailpayment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.paytrailpayment.dto.request.*;
import io.paytrailpayment.dto.response.*;
import io.paytrailpayment.dto.response.data.*;
import io.paytrailpayment.exception.PaytrailClientException;
import io.paytrailpayment.exception.PaytrailCommunicationException;
import io.paytrailpayment.utilites.Constants;
import io.paytrailpayment.utilites.ResponseMessage;
import lombok.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
@Getter
@Setter
public class PaytrailClient extends Paytrail implements IPaytrail {
    public PaytrailClient(String merchantId, String secretKey, String platformName) {
        this.merchantId = merchantId;
        this.secretKey = secretKey;
        this.platformName = platformName;
    }

    @Override
    public CreatePaymentResponse createPayment(CreatePaymentRequest req) {
        try {
            ValidationResult validationResult = validateCreatePaymentRequest(req);
            if (!validationResult.isValid()) {
                CreatePaymentResponse response = new CreatePaymentResponse();
                response.setReturnCode(ResponseMessage.BAD_REQUEST.getCode());
                response.setReturnMessage(validationResult.getMessagesAsJson());
                return response;
            }
            return executeCreatePayment(req);
        } catch (PaytrailClientException | PaytrailCommunicationException e) {
            return new CreatePaymentResponse(e.getErrorCode(), e.getMessage(), null);
        }
    }

    private ValidationResult validateCreatePaymentRequest(CreatePaymentRequest req) {
        if (req == null) {
            Map<String, String> error = new HashMap<>();
            error.put("error", ResponseMessage.BAD_REQUEST.getDescription());
            return new ValidationResult(false, error);
        }
        return req.validate();
    }

    private CreatePaymentResponse executeCreatePayment(CreatePaymentRequest req) throws PaytrailCommunicationException, PaytrailClientException{
        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonRequest = mapper.writeValueAsString(req);
            String targetURL = Constants.API_ENDPOINT + "/payments";
            DataResponse data = this.handleRequest(Constants.POST_METHOD, targetURL, jsonRequest, null, null);

            if (data.getStatusCode() != ResponseMessage.CREATED.getCode()) {
                return new CreatePaymentResponse(data.getStatusCode(), data.getData(), null);
            } else {
                // Successfully created the payment and parse the result
                CreatePaymentData dataMapper = mapper.readValue(data.getData(), CreatePaymentData.class);
                return new CreatePaymentResponse(data.getStatusCode(), ResponseMessage.OK.getDescription(), dataMapper);
            }

        } catch (JsonProcessingException e) {
            throw new PaytrailClientException(ResponseMessage.RESPONSE_ERROR.getCode(), e.getMessage(), e);
        }
    }

    @Override
    public GetPaymentResponse getPaymentInfo(String transactionId) {
        try {
            ValidationResult validationResult = validateGetPaymentRequest(transactionId);
            if (!validationResult.isValid()) {
                GetPaymentResponse response = new GetPaymentResponse();
                response.setReturnCode(ResponseMessage.BAD_REQUEST.getCode());
                response.setReturnMessage(validationResult.getMessagesAsJson());
                return response;
            }
            return executeGetPayment(transactionId);
        } catch (PaytrailClientException | PaytrailCommunicationException e) {
            return new GetPaymentResponse(e.getErrorCode(), e.getMessage(), null);
        }
    }

    private ValidationResult validateGetPaymentRequest(String transactionId) {
        if (transactionId == null || transactionId.trim().isEmpty()) {
            Map<String, String> error = new HashMap<>();
            error.put("error", ResponseMessage.BAD_REQUEST.getDescription());
            return new ValidationResult(false, error);
        }
        return new ValidationResult(true, Collections.emptyMap());
    }
    private GetPaymentResponse executeGetPayment(String transactionId) throws PaytrailClientException {
        ObjectMapper mapper = new ObjectMapper();
        GetPaymentResponse res = new GetPaymentResponse();
        try {
            String targetURL = Constants.API_ENDPOINT + "/payments/" + transactionId;
            DataResponse data = this.handleRequest(Constants.GET_METHOD, targetURL, null, transactionId, null);
            if (data.getStatusCode() != ResponseMessage.OK.getCode()) {
                res.setReturnCode(data.getStatusCode());
                res.setReturnMessage(data.getData());
            } else {
                GetPaymentData dataMapper = mapper.readValue(data.getData(), GetPaymentData.class);
                res.setReturnCode(data.getStatusCode());
                res.setReturnMessage(ResponseMessage.OK.getDescription());
                res.setData(dataMapper);
            }
            return res;
        } catch (JsonProcessingException e) {
            throw new PaytrailClientException(ResponseMessage.RESPONSE_ERROR.getCode(), e.getMessage(), e);
        }
    }

    @Override
    public CreateRefundResponse createRefundRequest(CreateRefundRequest req, String transactionId) {
        try {
            ValidationResult validationResult = validateCreateRefundRequest(req, transactionId);
            if (!validationResult.isValid()) {
                CreateRefundResponse response = new CreateRefundResponse();
                response.setReturnCode(ResponseMessage.BAD_REQUEST.getCode());
                response.setReturnMessage(validationResult.getMessagesAsJson());
                return response;
            }
            return executeCreateRefund(req, transactionId);
        } catch (PaytrailClientException | PaytrailCommunicationException e) {
            return new CreateRefundResponse(e.getErrorCode(), e.getMessage(), null);
        }
    }

    private ValidationResult validateCreateRefundRequest(CreateRefundRequest req, String transactionId) {
        if (req == null || transactionId == null || transactionId.isEmpty()) {
            Map<String, String> error = new HashMap<>();
            error.put("error", ResponseMessage.BAD_REQUEST.getDescription());
            return new ValidationResult(false, error);
        }
        return req.validate();
    }

    private CreateRefundResponse executeCreateRefund(CreateRefundRequest req, String transactionId) throws PaytrailClientException, PaytrailCommunicationException {
        CreateRefundResponse res = new CreateRefundResponse();
        ObjectMapper mapper = new ObjectMapper();
        try {
            String jsonRequest = mapper.writeValueAsString(req);
            String targetURL = Constants.API_ENDPOINT + "/payments/" + transactionId + "/refund";
            DataResponse data = this.handleRequest(Constants.POST_METHOD, targetURL, jsonRequest, transactionId, null);

            if (data.getStatusCode() != ResponseMessage.CREATED.getCode()) {
                res.setReturnCode(data.getStatusCode());
                res.setReturnMessage(data.getData());
            } else {
                CreateRefundData dataMapper = mapper.readValue(data.getData(), CreateRefundData.class);
                res.setReturnCode(data.getStatusCode());
                res.setReturnMessage(ResponseMessage.OK.getDescription());
                res.setData(dataMapper);
            }
            return res;
        } catch (JsonProcessingException e) {
            throw new PaytrailClientException(ResponseMessage.RESPONSE_ERROR.getCode(), e.getMessage(), e);
        }
    }
    @Override
    public AddCardFormResponse createAddCardFormRequest(AddCardFormRequest req) {
        try {
            ValidationResult validationResult = validateAddCardFormRequest(req);
            if (!validationResult.isValid()) {
                AddCardFormResponse response = new AddCardFormResponse();
                response.setReturnCode(ResponseMessage.BAD_REQUEST.getCode());
                response.setReturnMessage(validationResult.getMessagesAsJson());
                return response;
            }
            return executeCreateAddCardForm(req);
        } catch (PaytrailClientException | PaytrailCommunicationException e) {
            return new AddCardFormResponse(e.getErrorCode(), e.getMessage(), null);
        }
    }

    private ValidationResult validateAddCardFormRequest(AddCardFormRequest req) {
        if (req == null) {
            Map<String, String> error = new HashMap<>();
            error.put("error", ResponseMessage.BAD_REQUEST.getDescription());
            return new ValidationResult(false, error);
        }
        return req.validate();
    }

    private AddCardFormResponse executeCreateAddCardForm(AddCardFormRequest req) throws PaytrailCommunicationException, PaytrailClientException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonRequest = mapper.writeValueAsString(req);
            String targetURL = Constants.API_ENDPOINT + "/tokenization/addcard-form";
            DataResponse data = this.handleRequest(Constants.POST_METHOD, targetURL, jsonRequest, null, null);

            if (data.getStatusCode() != ResponseMessage.CREATED.getCode()) {
                return new AddCardFormResponse(data.getStatusCode(), data.getData(), null);
            } else {
                // Successfully created the add card form and parse the result
                AddCardFormData dataMapper = mapper.readValue(data.getData(), AddCardFormData.class);
                return new AddCardFormResponse(data.getStatusCode(), ResponseMessage.OK.getDescription(), dataMapper);
            }
        } catch (JsonProcessingException e) {
            throw new PaytrailClientException(ResponseMessage.RESPONSE_ERROR.getCode(), e.getMessage(), e);
        }
    }

    @Override
    public CreateMitOrCitPaymentResponse createMitPaymentCharge(CreateMitOrCitPaymentRequest req) {
        CreateMitOrCitPaymentResponse res = new CreateMitOrCitPaymentResponse();
        try {
            ValidationResult validationResult = validateCreateMitOrCitPaymentRequest(req);
            if (!validationResult.isValid()) {
                res.setReturnCode(ResponseMessage.BAD_REQUEST.getCode());
                res.setReturnMessage(validationResult.getMessagesAsJson());
                return res;
            }
            return executeCreateMitPaymentCharge(req);
        } catch (PaytrailClientException | PaytrailCommunicationException e) {
            res.setReturnCode(ResponseMessage.EXCEPTION.getCode());
            res.setReturnMessage(e.getMessage());
            return res;
        }
    }

    private ValidationResult validateCreateMitOrCitPaymentRequest(CreateMitOrCitPaymentRequest req) {
        if (req == null) {
            Map<String, String> error = new HashMap<>();
            error.put("error", ResponseMessage.BAD_REQUEST.getDescription());
            return new ValidationResult(false, error);
        }
        return req.validate();
    }

    private CreateMitOrCitPaymentResponse executeCreateMitPaymentCharge(CreateMitOrCitPaymentRequest req) throws PaytrailClientException, PaytrailCommunicationException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonRequest = mapper.writeValueAsString(req);
            String targetURL = Constants.API_ENDPOINT + "/payments/token/mit/charge";

            DataResponse data = this.handleRequest(Constants.POST_METHOD, targetURL, jsonRequest, null, null);

            if (data.getStatusCode() != ResponseMessage.OK.getCode()) {
                return new CreateMitOrCitPaymentResponse(data.getStatusCode(), data.getData(), null);
            } else {
                CreateMitPaymentChargeData dataMapper = mapper.readValue(data.getData(), CreateMitPaymentChargeData.class);
                return new CreateMitOrCitPaymentResponse(ResponseMessage.OK.getCode(), ResponseMessage.OK.getDescription(), dataMapper);
            }
        } catch (JsonProcessingException e) {
            throw new PaytrailClientException(ResponseMessage.RESPONSE_ERROR.getCode(), e.getMessage(), e);
        }
    }

    public CreateMitOrCitPaymentResponse createMitPaymentAuthorizationHold(CreateMitOrCitPaymentRequest createMitPaymentAuthorizationHold) {
        CreateMitOrCitPaymentResponse res = new CreateMitOrCitPaymentResponse();
        try {
            // Validate create mit payment authorization hold
            ValidationResult validationResult = validateCreateMitOrCitPaymentRequest(createMitPaymentAuthorizationHold);
            if (!validationResult.isValid()) {
                res.setReturnCode(ResponseMessage.BAD_REQUEST.getCode());
                res.setReturnMessage(validationResult.getMessagesAsJson());
                return res;
            }
            return executeCreateMitPaymentAuthorizationHold(createMitPaymentAuthorizationHold);
        } catch (Exception ex) {
            res.setReturnCode(ResponseMessage.EXCEPTION.getCode());
            res.setReturnMessage(ex.toString());
            return res;
        }
    }
    private CreateMitOrCitPaymentResponse executeCreateMitPaymentAuthorizationHold(CreateMitOrCitPaymentRequest req) throws PaytrailCommunicationException, PaytrailClientException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonRequest = mapper.writeValueAsString(req);
            String targetURL = Constants.API_ENDPOINT + "/payments/token/mit/authorization-hold";
            DataResponse data = this.handleRequest(Constants.POST_METHOD, targetURL, jsonRequest, null, null);

            if (data.getStatusCode() != ResponseMessage.OK.getCode()) {
                return new CreateMitOrCitPaymentResponse(data.getStatusCode(), data.getData(), null);
            } else {
                // Successfully created the MIT payment authorization hold and parse the result
                CreateMitPaymentChargeData dataMapper = mapper.readValue(data.getData(), CreateMitPaymentChargeData.class);
                return new CreateMitOrCitPaymentResponse(data.getStatusCode(), ResponseMessage.OK.getDescription(), dataMapper);
            }
        } catch (JsonProcessingException e) {
            throw new PaytrailClientException(ResponseMessage.RESPONSE_ERROR.getCode(), e.getMessage(), e);
        }
    }

    @Override
    public CreateMitOrCitPaymentResponse createMitPaymentCommit(CreateMitOrCitPaymentRequest req, String transactionId) {
        CreateMitOrCitPaymentResponse res = new CreateMitOrCitPaymentResponse();
        try {
            ValidationResult validationResult = validateCreateMitOrCitPaymentRequest(req);
            if (!validationResult.isValid()) {
                res.setReturnCode(ResponseMessage.BAD_REQUEST.getCode());
                res.setReturnMessage(validationResult.getMessagesAsJson());
                return res;
            }

            if (transactionId == null || transactionId.isEmpty()) {
                res.setReturnCode(ResponseMessage.RESPONSE_NULL.getCode());
                res.setReturnMessage("transactionId cannot be null");
                return res;
            }

            return executeCreateMitOrCitPaymentCommit(req, transactionId);
        } catch (Exception ex) {
            res.setReturnCode(ResponseMessage.EXCEPTION.getCode());
            res.setReturnMessage(ex.toString());
            return res;
        }
    }

    private CreateMitOrCitPaymentResponse executeCreateMitOrCitPaymentCommit(CreateMitOrCitPaymentRequest req, String transactionId) throws PaytrailCommunicationException, PaytrailClientException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonRequest = mapper.writeValueAsString(req);
            String targetURL = Constants.API_ENDPOINT + "/payments/" + transactionId + "token/mit/commit/";

            DataResponse data = this.handleRequest(Constants.POST_METHOD, targetURL, jsonRequest, transactionId, null);

            if (data.getStatusCode() != ResponseMessage.OK.getCode()) {
                return new CreateMitOrCitPaymentResponse(data.getStatusCode(), data.getData(), null);
            } else {
                CreateMitPaymentChargeData dataMapper = mapper.readValue(data.getData(), CreateMitPaymentChargeData.class);
                return new CreateMitOrCitPaymentResponse(ResponseMessage.OK.getCode(), ResponseMessage.OK.getDescription(), dataMapper);
            }
        } catch (JsonProcessingException e) {
            throw new PaytrailClientException(ResponseMessage.RESPONSE_ERROR.getCode(), e.getMessage(), e);
        }
    }

    @Override
    public CreateMitOrCitPaymentResponse createCitPaymentCharge(CreateMitOrCitPaymentRequest req) {
        CreateMitOrCitPaymentResponse res = new CreateMitOrCitPaymentResponse();
        try {
            ValidationResult validationResult = validateCreateMitOrCitPaymentRequest(req);
            if (!validationResult.isValid()) {
                res.setReturnCode(ResponseMessage.BAD_REQUEST.getCode());
                res.setReturnMessage(validationResult.getMessagesAsJson());
                return res;
            }
            return executeCreateCitPaymentCharge(req);
        } catch (PaytrailClientException | PaytrailCommunicationException e) {
            res.setReturnCode(ResponseMessage.EXCEPTION.getCode());
            res.setReturnMessage(e.getMessage());
            return res;
        }
    }

    private CreateMitOrCitPaymentResponse executeCreateCitPaymentCharge(CreateMitOrCitPaymentRequest req) throws PaytrailClientException, PaytrailCommunicationException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonRequest = mapper.writeValueAsString(req);
            String targetURL = Constants.API_ENDPOINT + "/payments/token/cit/charge";

            DataResponse data = this.handleRequest(Constants.POST_METHOD, targetURL, jsonRequest, null, null);

            if (data.getStatusCode() != ResponseMessage.OK.getCode()) {
                return new CreateMitOrCitPaymentResponse(data.getStatusCode(), data.getData(), null);
            } else {
                CreateMitPaymentChargeData dataMapper = mapper.readValue(data.getData(), CreateMitPaymentChargeData.class);
                return new CreateMitOrCitPaymentResponse(ResponseMessage.OK.getCode(), ResponseMessage.OK.getDescription(), dataMapper);
            }
        } catch (JsonProcessingException e) {
            throw new PaytrailClientException(ResponseMessage.RESPONSE_ERROR.getCode(), e.getMessage(), e);
        }
    }

    @Override
    public CreateMitOrCitPaymentResponse createCitPaymentAuthorizationHold(CreateMitOrCitPaymentRequest req) {
        CreateMitOrCitPaymentResponse res = new CreateMitOrCitPaymentResponse();
        try {
            ValidationResult validationResult = validateCreateMitOrCitPaymentRequest(req);
            if (!validationResult.isValid()) {
                res.setReturnCode(ResponseMessage.BAD_REQUEST.getCode());
                res.setReturnMessage(validationResult.getMessagesAsJson());
                return res;
            }
            return executeCreateCitPaymentAuthorizationHold(req);
        } catch (PaytrailClientException | PaytrailCommunicationException e) {
            res.setReturnCode(ResponseMessage.EXCEPTION.getCode());
            res.setReturnMessage(e.getMessage());
            return res;
        }
    }

    private CreateMitOrCitPaymentResponse executeCreateCitPaymentAuthorizationHold(CreateMitOrCitPaymentRequest req) throws PaytrailClientException, PaytrailCommunicationException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonRequest = mapper.writeValueAsString(req);
            String targetURL = Constants.API_ENDPOINT + "/payments/token/cit/authorization-hold";

            DataResponse data = this.handleRequest(Constants.POST_METHOD, targetURL, jsonRequest, null, null);

            if (data.getStatusCode() != ResponseMessage.OK.getCode()) {
                return new CreateMitOrCitPaymentResponse(data.getStatusCode(), data.getData(), null);
            } else {
                CreateMitPaymentChargeData dataMapper = mapper.readValue(data.getData(), CreateMitPaymentChargeData.class);
                return new CreateMitOrCitPaymentResponse(ResponseMessage.OK.getCode(), ResponseMessage.OK.getDescription(), dataMapper);
            }
        } catch (JsonProcessingException e) {
            throw new PaytrailClientException(ResponseMessage.RESPONSE_ERROR.getCode(), e.getMessage(), e);
        }
    }

    @Override
    public CreateMitOrCitPaymentResponse createCitPaymentCommit(CreateMitOrCitPaymentRequest req, String transactionId) {
        CreateMitOrCitPaymentResponse res = new CreateMitOrCitPaymentResponse();
        try {
            ValidationResult validationResult = validateCreateMitOrCitPaymentRequest(req);
            if (!validationResult.isValid()) {
                res.setReturnCode(ResponseMessage.BAD_REQUEST.getCode());
                res.setReturnMessage(validationResult.getMessagesAsJson());
                return res;
            }

            if (transactionId == null || transactionId.isEmpty()) {
                res.setReturnCode(ResponseMessage.RESPONSE_NULL.getCode());
                res.setReturnMessage("transactionId cannot be null");
                return res;
            }

            return executeCreateMitOrCitPaymentCommit(req, transactionId);
        } catch (Exception ex) {
            res.setReturnCode(ResponseMessage.EXCEPTION.getCode());
            res.setReturnMessage(ex.toString());
            return res;
        }
    }

    @Override
    public PayAddCardResponse payAndAddCard(PayAddCardRequest req) {
        PayAddCardResponse res = new PayAddCardResponse();
        try {
            ValidationResult validationResult = validatePayAndAddCardRequest(req);
            if (!validationResult.isValid()) {
                res.setReturnCode(ResponseMessage.BAD_REQUEST.getCode());
                res.setReturnMessage(validationResult.getMessagesAsJson());
                return res;
            }
            return executePayAndAddCard(req);
        } catch (Exception ex) {
            res.setReturnCode(ResponseMessage.EXCEPTION.getCode());
            res.setReturnMessage(ex.toString());
            return res;
        }
    }

    private ValidationResult validatePayAndAddCardRequest(PayAddCardRequest req) {
        if (req == null) {
            Map<String, String> error = new HashMap<>();
            error.put("error", ResponseMessage.BAD_REQUEST.getDescription());
            return new ValidationResult(false, error);
        }
        return req.validate();
    }

    private PayAddCardResponse executePayAndAddCard(PayAddCardRequest req) throws PaytrailClientException, PaytrailCommunicationException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonRequest = mapper.writeValueAsString(req);
            String targetURL = Constants.API_ENDPOINT + "/tokenization/pay-and-add-card";

            DataResponse data = this.handleRequest(Constants.POST_METHOD, targetURL, jsonRequest, null, null);

            if (data.getStatusCode() != ResponseMessage.OK.getCode()) {
                return new PayAddCardResponse(data.getStatusCode(), data.getData(), null);
            } else {
                PayAddCardData dataMapper = mapper.readValue(data.getData(), PayAddCardData.class);
                return new PayAddCardResponse(ResponseMessage.OK.getCode(), ResponseMessage.OK.getDescription(), dataMapper);
            }
        } catch (JsonProcessingException e) {
            throw new PaytrailClientException(ResponseMessage.RESPONSE_ERROR.getCode(), e.getMessage(), e);
        }
    }

}

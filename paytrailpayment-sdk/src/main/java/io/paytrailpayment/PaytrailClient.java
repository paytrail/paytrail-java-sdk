package io.paytrailpayment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
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
import java.util.List;
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

    private ValidationResult validateGetPaymentProvidersRequest(GetPaymentProvidersRequest req) {
        if (req == null) {
            Map<String, String> error = new HashMap<>();
            error.put("error", ResponseMessage.BAD_REQUEST.getDescription());
            return new ValidationResult(false, error);
        }
        return req.validate();
    }

    private GetPaymentProvidersResponse executeGetPaymentProviders(GetPaymentProvidersRequest req) throws PaytrailCommunicationException, PaytrailClientException {
        ObjectMapper mapper = new ObjectMapper();
        GetPaymentProvidersResponse res = new GetPaymentProvidersResponse();
        try {
            // Create new request
            String url = Constants.API_ENDPOINT + "/merchants/payment-providers?" + req.toString();
            DataResponse data = this.handleRequest(Constants.GET_METHOD, url, null, null, null);

            if (data.getStatusCode() != ResponseMessage.OK.getCode()) {
                res.setReturnCode(data.getStatusCode());
                res.setReturnMessage(data.getData());
            } else {
                List<GetPaymentProvidersData> dataMapper = mapper.readValue(data.getData(), new TypeReference<List<GetPaymentProvidersData>>() {});
                res.setReturnCode(data.getStatusCode());
                res.setReturnMessage(ResponseMessage.OK.getDescription());
                res.setData(dataMapper);
            }
            return res;
        } catch (JsonProcessingException e) {
            throw new PaytrailClientException(ResponseMessage.RESPONSE_ERROR.getCode(), e.getMessage(), e);
        }
    }

    public GetPaymentProvidersResponse getPaymentProviders(GetPaymentProvidersRequest req) {
        try {
            ValidationResult validationResult = validateGetPaymentProvidersRequest(req);
            if (!validationResult.isValid()) {
                GetPaymentProvidersResponse response = new GetPaymentProvidersResponse();
                response.setReturnCode(ResponseMessage.BAD_REQUEST.getCode());
                response.setReturnMessage(validationResult.getMessagesAsJson());
                return response;
            }
            return executeGetPaymentProviders(req);
        } catch (PaytrailClientException | PaytrailCommunicationException e) {
            GetPaymentProvidersResponse response = new GetPaymentProvidersResponse();
            response.setReturnCode(e.getErrorCode());
            response.setReturnMessage(e.getMessage());
            return response;
        }
    }

    private boolean validateGetGroupedPaymentProviders(GetGroupedPaymentProvidersResponse res, GetGroupedPaymentProvidersRequest req) {
        if (req == null) {
            res.setReturnCode(ResponseMessage.RESPONSE_NULL.getCode());
            res.setReturnMessage("Get payment grouped providers request cannot be null");
            return false;
        }

        ValidationResult validationResult = req.validate();
        if (!validationResult.isValid()) {
            res.setReturnCode(ResponseMessage.VALIDATION_FAILED.getCode());
            res.setReturnMessage(validationResult.getMessagesAsJson());
            return false;
        }

        return true;
    }

    private GetGroupedPaymentProvidersResponse executeGetGroupedPaymentProviders(GetGroupedPaymentProvidersRequest req) throws PaytrailCommunicationException, PaytrailClientException {
        ObjectMapper mapper = new ObjectMapper();
        GetGroupedPaymentProvidersResponse res = new GetGroupedPaymentProvidersResponse();
        try {
            // Create new request
            String url = Constants.API_ENDPOINT + "/merchants/grouped-payment-providers?" + req.toString();
            DataResponse data = this.handleRequest(Constants.GET_METHOD, url, null, null, null);

            if (data.getStatusCode() != ResponseMessage.OK.getCode()) {
                res.setReturnCode(data.getStatusCode());
                res.setReturnMessage(data.getData());
            } else {
                GetGroupedPaymentProvidersData dataMapper = mapper.readValue(data.getData(), GetGroupedPaymentProvidersData.class);
                res.setReturnCode(data.getStatusCode());
                res.setReturnMessage(ResponseMessage.OK.getDescription());
                res.setData(dataMapper);
            }
            return res;
        } catch (JsonProcessingException e) {
            throw new PaytrailClientException(ResponseMessage.RESPONSE_ERROR.getCode(), e.getMessage(), e);
        }
    }

    public GetGroupedPaymentProvidersResponse getGroupedPaymentProviders(GetGroupedPaymentProvidersRequest req) {
        GetGroupedPaymentProvidersResponse res = new GetGroupedPaymentProvidersResponse();
        try {
            if (!validateGetGroupedPaymentProviders(res, req)) {
                return res;
            }
            return executeGetGroupedPaymentProviders(req);
        } catch (PaytrailClientException | PaytrailCommunicationException e) {
            res.setReturnCode(ResponseMessage.EXCEPTION.getCode());
            res.setReturnMessage(e.getMessage());
            return res;
        }
    }
}

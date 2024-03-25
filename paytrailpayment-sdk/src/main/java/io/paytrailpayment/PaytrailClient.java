package io.paytrailpayment;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.paytrailpayment.dto.request.CreatePaymentRequest;
import io.paytrailpayment.dto.request.CreateRefundRequest;
import io.paytrailpayment.dto.request.ValidationResult;
import io.paytrailpayment.dto.response.CreatePaymentResponse;
import io.paytrailpayment.dto.response.CreateRefundResponse;
import io.paytrailpayment.dto.response.GetPaymentResponse;
import io.paytrailpayment.dto.response.data.CreateRefundData;
import io.paytrailpayment.dto.response.data.DataResponse;
import io.paytrailpayment.dto.response.data.CreatePaymentData;
import io.paytrailpayment.dto.response.data.GetPaymentData;
import io.paytrailpayment.exception.AppException;
import io.paytrailpayment.utilites.Constants;
import io.paytrailpayment.utilites.ResponseMessage;
import lombok.*;

import static io.paytrailpayment.utilites.Constants.GET_METHOD;

@NoArgsConstructor
@Getter @Setter
public class PaytrailClient extends Paytrail implements IPaytrail {
    public PaytrailClient(String merchantId, String secretKey, String platformName) {
        this.merchantId = merchantId;
        this.secretKey = secretKey;
        this.platformName = platformName;
    }

    @Override
    public CreatePaymentResponse createPayment(CreatePaymentRequest req) {
        CreatePaymentResponse res = new CreatePaymentResponse();

        try {
            if (!validateCreatePaymentRequest(req, res)) {
                return res;
            }

            ObjectMapper mapper = new ObjectMapper();
            String jsonRequest = mapper.writeValueAsString(req);
            res = createPayment(jsonRequest);

            return res;
        } catch (Exception e) {
            throw new AppException(ResponseMessage.RESPONSE_ERROR.getCode(), e.toString());
        }
    }

    @Override
    public GetPaymentResponse getPaymentInfo(String transactionId) {
        GetPaymentResponse res = new GetPaymentResponse();

        try {
            if (!validateGetPaymentRequest(transactionId, res)) {
                return res;
            }

            res = getPayment(transactionId);

            return res;
        } catch (Exception e) {
            throw new AppException(ResponseMessage.RESPONSE_ERROR.getCode(), e.toString());
        }
    }

    @Override
    public CreateRefundResponse createRefundRequest(CreateRefundRequest req, String transactionId) {
        CreateRefundResponse res = new CreateRefundResponse();

        try {
            if (!validateCreateRefundRequest(req, res, transactionId)) {
                return res;
            }

            ObjectMapper mapper = new ObjectMapper();
            String jsonRequest = mapper.writeValueAsString(req);
            res = createRefundRequest(transactionId, jsonRequest);

            return res;
        } catch (Exception e) {
            throw new AppException(ResponseMessage.RESPONSE_ERROR.getCode(), e.toString());
        }
    }

    private CreatePaymentResponse createPayment(String body) {
        CreatePaymentResponse res = new CreatePaymentResponse();

        try {
            String targetURL = Constants.API_ENDPOINT + "/payments";
            DataResponse data = this.handleRequest(Constants.POST_METHOD, targetURL, body, null, null);

            if (data.getStatusCode() != ResponseMessage.CREATED.getCode()) {
                res.setReturnCode(data.getStatusCode());
                res.setReturnMessage(data.getData());
            } else {
                ObjectMapper mapper = new ObjectMapper();
                CreatePaymentData dataMapper = mapper.readValue(data.getData(), CreatePaymentData.class);

                res.setReturnCode(data.getStatusCode());
                res.setReturnMessage(ResponseMessage.OK.getDescription());
                res.setData(dataMapper);
            }

            return res;

        } catch (Exception e) {
            throw new AppException(ResponseMessage.RESPONSE_ERROR.getCode(), e.toString());
        }
    }

    private GetPaymentResponse getPayment(String transactionId) {
        GetPaymentResponse res = new GetPaymentResponse();

        try {
            String targetURL = Constants.API_ENDPOINT + "/payments/" + transactionId;
            DataResponse data = this.handleRequest(GET_METHOD, targetURL, null, transactionId, null);

            if (data.getStatusCode() != ResponseMessage.OK.getCode()) {
                res.setReturnCode(data.getStatusCode());
                res.setReturnMessage(data.getData());
            } else {
                ObjectMapper mapper = new ObjectMapper();
                GetPaymentData dataMapper = mapper.readValue(data.getData(), GetPaymentData.class);

                res.setReturnCode(data.getStatusCode());
                res.setReturnMessage(ResponseMessage.OK.getDescription());
                res.setData(dataMapper);
            }

            return res;

        } catch (Exception e) {
            throw new AppException(ResponseMessage.RESPONSE_ERROR.getCode(), e.toString());
        }
    }

    private CreateRefundResponse createRefundRequest(String transactionId, String body) {
        CreateRefundResponse res = new CreateRefundResponse();

        try {
            String targetURL = Constants.API_ENDPOINT + "/payments/" + transactionId + "/refund";
            DataResponse data = this.handleRequest(Constants.POST_METHOD, targetURL, body, transactionId, null);

            if (data.getStatusCode() != ResponseMessage.CREATED.getCode()) {
                res.setReturnCode(data.getStatusCode());
                res.setReturnMessage(data.getData());
            } else {
                ObjectMapper mapper = new ObjectMapper();
                CreateRefundData dataMapper = mapper.readValue(data.getData(), CreateRefundData.class);

                res.setReturnCode(data.getStatusCode());
                res.setReturnMessage(ResponseMessage.OK.getDescription());
                res.setData(dataMapper);
            }

            return res;

        } catch (Exception e) {
            throw new AppException(ResponseMessage.RESPONSE_ERROR.getCode(), e.toString());
        }
    }

    private boolean validateCreatePaymentRequest(CreatePaymentRequest req, CreatePaymentResponse res) {
        if (req == null) {
            res.setReturnCode(ResponseMessage.BAD_REQUEST.getCode());
            res.setReturnMessage(ResponseMessage.BAD_REQUEST.getDescription());
            return false;
        }

        ValidationResult validationResult = req.validate();

        if (!validationResult.isValid()) {
            res.setReturnCode(ResponseMessage.BAD_REQUEST.getCode());
            res.setReturnMessage(validationResult.getMessage().toString());
            return false;
        }

        return true;
    }

    private boolean validateGetPaymentRequest(String transactionId, GetPaymentResponse res) {
        if (transactionId == null || transactionId.isEmpty()) {
            res.setReturnCode(ResponseMessage.BAD_REQUEST.getCode());
            res.setReturnMessage(ResponseMessage.BAD_REQUEST.getDescription());
            return false;
        }

        return true;
    }

    private boolean validateCreateRefundRequest(CreateRefundRequest req, CreateRefundResponse res, String transactionId) {
        if (req == null || transactionId == null || transactionId.isEmpty()) {
            res.setReturnCode(ResponseMessage.BAD_REQUEST.getCode());
            res.setReturnMessage(ResponseMessage.BAD_REQUEST.getDescription());
            return false;
        }

        ValidationResult validationResult = req.validate();

        if (!validationResult.isValid()) {
            res.setReturnCode(ResponseMessage.BAD_REQUEST.getCode());
            res.setReturnMessage(validationResult.getMessage().toString());
            return false;
        }

        return true;
    }
}

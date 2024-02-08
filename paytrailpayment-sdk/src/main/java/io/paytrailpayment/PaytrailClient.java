package io.paytrailpayment;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.paytrailpayment.dto.request.CreatePaymentRequest;
import io.paytrailpayment.dto.request.ValidationResult;
import io.paytrailpayment.dto.response.CreatePaymentResponse;
import io.paytrailpayment.dto.response.data.DataResponse;
import io.paytrailpayment.dto.response.data.CreatePaymentData;
import io.paytrailpayment.utilites.Constants;
import io.paytrailpayment.utilites.ResponseMessage;
import lombok.*;

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
            res.setReturnCode(ResponseMessage.RESPONSE_ERROR.getCode());
            res.setReturnMessage(e.toString());
            return res;
        }
    }

   
    private CreatePaymentResponse createPayment(String body) {
        CreatePaymentResponse res = new CreatePaymentResponse();

        try {
            String targetURL = Constants.API_ENDPOINT + "/payments";
            DataResponse data = this.handleRequest(Constants.POST_METHOD, targetURL, body, null, null);

            if (data.getStatusCode() != ResponseMessage.OK.getCode()
                    && data.getStatusCode() != ResponseMessage.CREATED.getCode()) {
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
            res.setReturnCode(ResponseMessage.RESPONSE_ERROR.getCode());
            res.setReturnMessage(e.toString());
            return res;
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
}

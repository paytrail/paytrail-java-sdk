package io.paytrailpayment.dto.response;

import io.paytrailpayment.dto.response.data.CreateRefundData;
import io.paytrailpayment.dto.response.data.GetPaymentData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetPaymentResponse extends Response {
    private GetPaymentData data;
    public GetPaymentResponse(int statusCode, String description, GetPaymentData dataMapper) {
        super(statusCode, description);
        this.data = dataMapper;
    }
}

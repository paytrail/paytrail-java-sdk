package io.paytrailpayment.dto.response;

import io.paytrailpayment.dto.response.data.CreatePaymentData;
import io.paytrailpayment.dto.response.data.CreateRefundData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateRefundResponse extends Response {
    private CreateRefundData data;

    public CreateRefundResponse(int statusCode, String description, CreateRefundData dataMapper) {
        super(statusCode, description);
        this.data = dataMapper;
    }
}

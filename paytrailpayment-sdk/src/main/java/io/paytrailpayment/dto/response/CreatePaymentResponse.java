package io.paytrailpayment.dto.response;

import io.paytrailpayment.dto.response.data.CreatePaymentData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePaymentResponse extends Response {
    private CreatePaymentData data;

    public CreatePaymentResponse(int statusCode, String description, CreatePaymentData dataMapper) {
        super(statusCode, description);
        this.data = dataMapper;
    }
}



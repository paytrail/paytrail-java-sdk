package io.paytrailpayment.dto.response;

import io.paytrailpayment.dto.response.data.CreateMitPaymentChargeData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateMitOrCitPaymentResponse extends Response {
    private CreateMitPaymentChargeData data;

    public CreateMitOrCitPaymentResponse(int statusCode, String description, CreateMitPaymentChargeData dataMapper) {
        super(statusCode, description);
        this.data = dataMapper;
    }
}

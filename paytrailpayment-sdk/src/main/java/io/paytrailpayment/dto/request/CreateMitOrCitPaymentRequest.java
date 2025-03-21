package io.paytrailpayment.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateMitOrCitPaymentRequest extends CreatePaymentRequest {
    private String token;

    @Override
    protected void specificValidate() {
        super.specificValidate();

        if (token == null || token.isEmpty()) {
            addValidationError("token", "token can't be null.");
        }
    }
}

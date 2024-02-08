package io.paytrailpayment.dto.request.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.paytrailpayment.dto.request.Request;
import io.paytrailpayment.dto.request.ValidationResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class CallbackUrl extends Request {
    /**
     * Called on successful payment.
     */
    private String success;

    /**
     * Called on cancelled payment.
     */
    private String cancel;

    @Override()
    protected ValidationResult specificValidate() {
        boolean isValid = true;
        StringBuilder message = new StringBuilder();

        if (success == null) {
            isValid = false;
            message.append("Url success can't be null. ");
        }

        if (cancel == null) {
            isValid = false;
            message.append("Url cancel can't be null. ");
        }
        return new ValidationResult(isValid, message);
    }
}

package io.paytrailpayment.dto.request.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.paytrailpayment.dto.request.Request;
import io.paytrailpayment.dto.request.ValidationResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

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
    protected void specificValidate() {
        if (StringUtils.isBlank(success)) {
            addValidationError("success", "Url success can't be null or empty.");
        }

        if (StringUtils.isBlank(cancel)) {
            addValidationError("cancel", "Url cancel can't be null or empty.");
        }
    }
}

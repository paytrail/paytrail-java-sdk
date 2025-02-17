package io.paytrailpayment.dto.request;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class GetTokenRequest extends Request {
    private String checkoutTokenizationId;

    @Override
    protected void specificValidate() {
        if (checkoutTokenizationId == null || checkoutTokenizationId.isEmpty()) {
            addValidationError("checkoutTokenizationId", "checkoutTokenizationId can't be null.");
        }
    }
}

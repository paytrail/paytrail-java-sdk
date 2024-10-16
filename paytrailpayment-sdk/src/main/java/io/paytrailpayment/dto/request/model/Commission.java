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
public class Commission extends Request {
    /**
     * Merchant who gets the commission.
     */
    private String merchant;

    /**
     * Amount of commission in currency's minor units, e.g. for Euros use cents. VAT not applicable.
     */
    private int amount;

    @Override()
    protected void specificValidate() {
        if (StringUtils.isBlank(merchant))
        {
            addValidationError("merchant", "Commission's merchant is null or empty. ");
        }

        if (amount < 0)
        {
            addValidationError("amount", "Commission's amount can't be a negative number. ");
        }

    }
}

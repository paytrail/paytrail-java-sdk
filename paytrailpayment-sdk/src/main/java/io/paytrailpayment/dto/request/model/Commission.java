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
    protected ValidationResult specificValidate() {
        boolean isValid = true;
        StringBuilder message = new StringBuilder();

        if (merchant == null || merchant.isEmpty())
        {
            message.append("Commission's merchant is null or empty. ");
            isValid = false;
        }

        if (amount < 0)
        {
            message.append("Commission's amount can't be a negative number. ");
            isValid = false;
        }

        return new ValidationResult(isValid, message);
    }
}

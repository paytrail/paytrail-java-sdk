package io.paytrailpayment.dto.request.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.paytrailpayment.dto.request.Request;
import io.paytrailpayment.dto.request.ValidationResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class RefundItem extends Request {
    /**
     * Total amount to refund this item, in currency's minor units.
     */
    private int amount;

    /**
     * The item unique identifier.
     */
    private String stamp;

    /**
     * Merchant unique identifier for the refund. Only for Shop-in-Shop payments, do not use for normal payments.
     */
    private String refundStamp;

    /**
     * Refund reference. Only for Shop-in-Shop payments, do not use for normal payments.
     */
    private String refundReference;

    /**
     * Shop-in-Shop commission return.
     */
    private Commission commission;

    @Override()
    protected ValidationResult specificValidate() {
        boolean isValid = true;
        StringBuilder message = new StringBuilder();

        if (amount < 0 || amount > 99999998)
        {
            isValid = false;
            message.append("Item's amount are invalid. ");
        }

        if (stamp == null || stamp.isEmpty())
        {
            isValid = false;
            message.append("Item's stamp can't be null. ");
        }

        if (stamp != null && stamp.length() > 200) {
            isValid = false;
            message.append("Item's stamp is more than 200 characters. ");
        }

        if (refundStamp != null && refundStamp.length() > 200) {
            isValid = false;
            message.append("Item's refundStamp is more than 200 characters. ");
        }

        if (commission != null)
        {
            ValidationResult itemValidationResult = commission.validate();

            if (!itemValidationResult.isValid()) {
                isValid = false;
                message.append(itemValidationResult.getMessage());
            }
        }

        return new ValidationResult(isValid, message);
    }
}

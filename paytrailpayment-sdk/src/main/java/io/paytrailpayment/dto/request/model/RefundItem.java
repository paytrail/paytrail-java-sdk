package io.paytrailpayment.dto.request.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.paytrailpayment.dto.request.Request;
import io.paytrailpayment.dto.request.ValidationResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

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
    protected void specificValidate() {
        if (amount < 0 || amount > 99999998)
        {
            addValidationError("amount", "Item's amount is invalid. ");
        }

        if (StringUtils.isBlank(stamp))
        {
            addValidationError("stamp", "Item's stamp can't be null or empty.");
        } else if (stamp.length() > 200) {
            addValidationError("stamp","Item's stamp is more than 200 characters. ");
        }

        if (Objects.nonNull(refundStamp) && refundStamp.length() > 200) {
            addValidationError("refundStamp", "Item's refund stamp is more than 200 characters. ");
        }

        if (commission != null)
        {
            ValidationResult itemValidationResult = commission.validate();
            if (!itemValidationResult.isValid()) {
                addValidationError("commission", itemValidationResult.getMessagesAsJson());
            }
        }
    }
}

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
public class Item extends Request {
    /**
     * Price per unit, in each country's minor unit, e.g. for Euros use cents.
     */
    private int unitPrice;

    /**
     * Quantity, how many items ordered. Negative values are not supported.
     */
    private int units;

    /**
     * VAT percentage
     */
    private int vatPercentage;

    /**
     * Merchant product code. May appear on invoices of certain payment methods. Maximum of 100 characters
     */
    private String productCode;

    /**
     * Item description. May appear on invoices of certain payment methods. Maximum of 1000 characters.
     */
    private String description;

    /**
     * Merchant specific item category.
     */
    private String category;

    /**
     * Item level order ID (suborder ID). Mainly useful for Shop-in-Shop purchases.
     */
    private String orderId;

    /**
     * Unique identifier for this item. Required for Shop-in-Shop payments. Required for item refunds.
     */
    private String stamp;

    /**
     * Reference for this item. Required for Shop-in-Shop payments.
     */
    private String reference;

    /**
     * Merchant ID for the item. Required for Shop-in-Shop payments, do not use for normal payments.
     */
    private String merchant;

    /**
     * Shop-in-Shop commission. Do not use for normal payments.
     */
    private Commission commission;

    @Override()
    protected ValidationResult specificValidate() {
        boolean isValid = true;
        StringBuilder message = new StringBuilder();

        if (unitPrice < 0) {
            isValid = false;
            message.append("Item's unitPrice can't be a negative number. ");
        }

        if (units < 0) {
            isValid = false;
            message.append("Item's units can't be a negative number. ");
        }

        if (vatPercentage < 0) {
            isValid = false;
            message.append("Item's vatPercentage can't be a negative number. ");
        }

        if (productCode == null) {
            isValid = false;
            message.append("Item's productCode can't be null. ");
        } else if (productCode.length() > 100) {
            isValid = false;
            message.append("Item's productCode is more than 100 characters. ");
        }

        if (description != null && description.length() > 1000) {
            isValid = false;
            message.append("Item's description is more than 1000 characters. ");
        }

        return new ValidationResult(isValid, message);
    }
}


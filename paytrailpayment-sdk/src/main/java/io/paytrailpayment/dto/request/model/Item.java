package io.paytrailpayment.dto.request.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.paytrailpayment.dto.request.Request;
import io.paytrailpayment.dto.request.ValidationResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

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
     * VAT percentage, with one decimal precision.
     */
    private BigDecimal vatPercentage;

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
    protected void specificValidate() {
        if (unitPrice < 0) {
            addValidationError("unitPrice", "Item's unit Price can't be a negative number.");
        }

        if (units < 0 || units > 99999998) {
            addValidationError("units", "Item's units are invalid. ");
        }

        if (vatPercentage == null || vatPercentage.compareTo(BigDecimal.ZERO) < 0) {
            addValidationError("vatPercentage", "Item's vat Percentage can't be null or a negative number.");
        } else {
            // Check if vatPercentage has more than one decimal place
            int scale = vatPercentage.stripTrailingZeros().scale();
            if (scale > 1) {
                addValidationError("vatPercentage", "Item's vat Percentage can't have more than one decimal place.");
            }
        }

        if (productCode == null) {
            addValidationError("productCode", "Item's product Code can't be null or empty.");
        } else if (productCode.length() > 100) {
            addValidationError("productCode", "Item's product Code is more than 100 characters.");
        }

        if (category != null && category.length() > 100) {
            addValidationError("category", "Item's category is more than 100 characters.");
        }

        if (description != null && description.length() > 1000) {
            addValidationError("description", "Item's description is more than 1000 characters.");
        }

    }
}


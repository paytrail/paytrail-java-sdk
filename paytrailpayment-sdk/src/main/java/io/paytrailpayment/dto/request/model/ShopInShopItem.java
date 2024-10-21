package io.paytrailpayment.dto.request.model;

import com.fasterxml.jackson.annotation.JsonInclude;
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
public class ShopInShopItem extends Item {
    /**
     * Item level order ID (suborder ID). Mainly useful for Shop-in-Shop purchases.
     */
    private String orderId;

    /**
     * Merchant ID for the item. Required for Shop-in-Shop payments, do not use for normal payments.
     */
    private String merchant;

    /**
     * Shop-in-Shop commission. Do not use for normal payments.
     */
    private Commission commission;

    public ShopInShopItem(int unitPrice, int units, int vatPercentage, String productCode, String category, String description, String stamp, String reference, String orderId, String merchant, Commission commission) {
        super(unitPrice, units, vatPercentage, productCode, description, category, stamp, reference);
        this.orderId = orderId;
        this.merchant = merchant;
        this.commission = commission;
    }

    public ShopInShopItem(int unitPrice, int units, int vatPercentage, String productCode, String category, String description, String stamp, String reference) {
        super(unitPrice, units, vatPercentage, productCode, description, category, stamp, reference);
    }

    public ShopInShopItem(int unitPrice, int units, int vatPercentage, String productCode, String category,
                          String description, String merchant, String stamp, String reference, String orderId) {
        super(unitPrice, units, vatPercentage, productCode, description, category, stamp, reference);
        this.merchant = merchant;
        this.orderId = orderId;
    }

    @Override
    protected void specificValidate() {
        super.specificValidate();

        if (orderId == null) {
            addValidationError("orderId", "Item's orderId can't be null.");
        }


        if (merchant == null || merchant.isEmpty()) {
            addValidationError("merchant", "Item's merchant can't be null or empty.");
        }

        if (commission != null) {
            ValidationResult commissionValidationResult = commission.validate();
            if (!commissionValidationResult.isValid()) {
                addValidationError("commission", commissionValidationResult.getMessagesAsJson());
            }
        }
    }
}

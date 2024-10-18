package io.paytrailpayment.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.paytrailpayment.dto.request.model.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class PayAddCardRequest extends Request {
    private String stamp;
    private String reference;
    private int amount;
    private PaytrailCurrency currency;
    private PaytrailLanguage language;
    private String orderId;
    private List<Item> items;
    private Customer customer;
    private Address deliveryAddress;
    private Address invoicingAddress;
    private boolean manualInvoiceActivation;
    private CallbackUrl redirectUrls;
    private CallbackUrl callbackUrls;
    private int callbackDelay;
    private List<String> groups;
    private boolean usePricesWithoutVat;

    @Override
    protected void specificValidate() {
        StringBuilder message = new StringBuilder();

        if (stamp == null || stamp.isEmpty()) {
            addValidationError("stamp", "Stamp can't be null or empty.");
        } else if (stamp.length() > 200) {
            addValidationError("stamp", "Stamp is more than 200 characters.");
        }

        if (reference == null || reference.isEmpty()) {
            addValidationError("reference", "Reference can't be null or empty.");
        } else if (reference.length() > 200) {
            addValidationError("reference", "Reference is more than 200 characters.");
        }

        if (amount < 0) {
            addValidationError("amount", "Amount can't be less than zero.");
        } else if (amount > 99999999) {
            addValidationError("amount", "Amount can't be more than 99999999.");
        }

        if (customer == null) {
            addValidationError("customer", "Customer can't be null.");
        } else {
            ValidationResult customerValidationResult = customer.validate();
            if (!customerValidationResult.isValid()) {
                addValidationError("customer", customerValidationResult.getMessagesAsJson());
            }
        }

        if (items != null) {
            for (Item item : items) {
                ValidationResult itemValidationResult = item.validate();
                if (!itemValidationResult.isValid()) {
                    addValidationError("item", itemValidationResult.getMessagesAsJson());
                    break;
                }
            }
        }

        if (deliveryAddress != null) {
            ValidationResult deliveryAddressValidationResult = deliveryAddress.validate();
            if (!deliveryAddressValidationResult.isValid()) {
                addValidationError("deliveryAddress", deliveryAddressValidationResult.getMessagesAsJson());
            }
        }

        if (invoicingAddress != null) {
            ValidationResult invoicingAddressValidationResult = invoicingAddress.validate();
            if (!invoicingAddressValidationResult.isValid()) {
                addValidationError("invoicingAddress", invoicingAddressValidationResult.getMessagesAsJson());
            }
        }

        if (redirectUrls == null) {
            addValidationError("redirectUrls", "Object redirectUrls can't be null.");
        } else {
            ValidationResult redirectUrlsValidationResult = redirectUrls.validate();
            if (!redirectUrlsValidationResult.isValid()) {
                addValidationError("redirectUrls", redirectUrlsValidationResult.getMessagesAsJson());
            }
        }

        if (callbackUrls == null) {
            addValidationError("callbackUrls", "Object callbackUrls can't be null.");
        } else {
            ValidationResult callbackUrlsValidationResult = callbackUrls.validate();
            if (!callbackUrlsValidationResult.isValid()) {
                addValidationError("callbackUrls", callbackUrlsValidationResult.getMessagesAsJson());
            }
        }

        if (groups != null) {
            Set<String> allowedGroups = EnumSet.allOf(PaytrailPaymentMethodGroup.class).stream()
                    .map(Enum::toString)
                    .collect(Collectors.toSet());
            for (String group : groups) {
                if (!allowedGroups.contains(group)) {
                    addValidationError("groups", "Value '" + group + "' is not in the list of allowed payment methods.");
                }
            }
        }
    }
}

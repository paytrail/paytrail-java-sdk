package io.paytrailpayment.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.paytrailpayment.dto.request.model.*;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class CreatePaymentRequest extends Request {
    /**
     * Merchant unique identifier for the order. Maximum of 200 characters.
     */
    private String stamp;

    /**
     * Order reference. Maximum of 200 characters.
     */
    private String reference;

    /**
     * Total amount of the payment in paytrailCurrency's minor units, e.g. for Euros use cents.
     */
    private int amount;

    /**
     * PaytrailCurrency, only EUR supported at the moment.
     */
    private PaytrailCurrency currency;

    /**
     * Payment's paytrailLanguage, currently supported are FI, SV, and EN.
     */
    private PaytrailLanguage language;

    /**
     * Order ID. Used for e.g. Walley/Collector payments order ID.
     */
    private String orderId;

    /**
     * Array of items. Always required for Shop-in-Shop payments.
     */
    private List<Item> items;

    /**
     * Customer information.
     */
    private Customer customer;

    /**
     * Delivery address.
     */
    private Address deliveryAddress;

    /**
     * Invoicing address
     */
    private Address invoicingAddress;

    /**
     * If paid with invoice payment method, the invoice will not be activated automatically immediately.
     */
    private boolean manualInvoiceActivation;

    /**
     * Where to redirect browser after a payment is paid or cancelled.
     */
    private CallbackUrl redirectUrls;

    /**
     * Which url to ping after this payment is paid or cancelled.
     */
    private CallbackUrl callbackUrls;

    /**
     * Callback URL polling delay in seconds. If callback URLs are given, the call can be delayed up to 900 seconds. Default: 0
     */
    private int callbackDelay;

    /**
     * Instead of all enabled payment methods, return only those of given groups.
     */
    private List<String> groups;

    /**
     * If true, amount and items.unitPrice should be sent to API not including VAT, and final
     * amount is calculated by Paytrail's system using the items' unitPrice and vatPercentage
     * (with amounts rounded to closest cent).
     */
    private boolean usePricesWithoutVat;

    @Override()
    protected void specificValidate() {
        StringBuilder message = new StringBuilder();
        boolean isValid = true;

        if (amount <= 0) {
            addValidationError("amount", "Amount must be more than zero.");
        } else if (amount > 99999999) {
            addValidationError("amount", "Amount must be less than 99999999.");
        }

//        if (orderId == null || orderId.isEmpty()) {
//            if (reference == null || reference.isEmpty()) {
//                addValidationError("orderId", "OrderId is not provided, then reference must be provided. ");
//            }
//        }

        if (items != null) {
            for (Item item : items) {
                ValidationResult itemValidationResult = item.validate();
                if (!itemValidationResult.isValid()) {
                    addValidationError("item", itemValidationResult.getMessagesAsJson());
                    // Assuming we stop checking further items after the first invalid one
                    break;
                }
            }

            // Calculate total amount from items
            int itemsTotal = items.stream()
                    .mapToInt(item -> item.getUnitPrice() * item.getUnits())
                    .sum();
            if (amount != itemsTotal) {
                addValidationError("amount", "Amount doesn't match total of items.");
            }
        } else {
            addValidationError("items", "Items list cannot be null.");
        }


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

        if (currency == null) {
            addValidationError("currency", "Currency can't be null.");
        }

        if (language == null) {
            addValidationError("language", "Language can't be null.");
        }

        if (customer == null) {
            addValidationError("customer", "Customer can't be null.");
        } else {
            ValidationResult customerValidationResult = customer.validate();
            if (!customerValidationResult.isValid()) {
                // Prefix is assumed to be part of ValidationResult's message itself for clarity
                addValidationError("customer", customerValidationResult.getMessagesAsJson());
            }
        }

        // Validation for redirectUrls
        if (redirectUrls == null) {
            addValidationError("redirectUrls", "Object redirectUrls can't be null.");
        } else {
            ValidationResult redirectUrlsValidationResult = redirectUrls.validate();
            if (!redirectUrlsValidationResult.isValid()) {
                addValidationError("redirectUrls", redirectUrlsValidationResult.getMessagesAsJson());
            }
        }

        if (callbackUrls != null) {
            ValidationResult callbackUrlsValidationResult = callbackUrls.validate();
            if (!callbackUrlsValidationResult.isValid()) {
                addValidationError("callbackUrls", callbackUrlsValidationResult.getMessagesAsJson());
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

// Checking if provided payment method groups are valid
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

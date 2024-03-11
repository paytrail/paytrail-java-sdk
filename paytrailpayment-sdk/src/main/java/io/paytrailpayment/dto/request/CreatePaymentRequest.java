package io.paytrailpayment.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.paytrailpayment.dto.request.model.*;

import java.util.Arrays;
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
     * Total amount of the payment in currency's minor units, e.g. for Euros use cents.
     */
    private int amount;

    /**
     * Currency, only EUR supported at the moment.
     */
    private String currency;

    /**
     * Payment's language, currently supported are FI, SV, and EN.
     */
    private String language;

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
    protected ValidationResult specificValidate() {
        String[] supportedCurrencies = {"EUR"};
        String[] supportedLanguages = {"FI", "SV", "EN"};

        StringBuilder message = new StringBuilder();
        boolean isValid = true;

        if (amount <= 0) {
            isValid = false;
            message.append("Amount must be more than zero. ");
        } else if (amount > 99999999) {
            isValid = false;
            message.append("Amount can't be more than 99999999. ");
        }

        if (items != null) {
            for (Item item : items) {
                ValidationResult itemValidationResult = item.validate();
                if (!itemValidationResult.isValid()) {
                    isValid = false;
                    message.append(itemValidationResult.getMessage());
                    break;
                }
            }

            // Calculate total amount from items.
            int itemsTotal = items.stream()
                    .mapToInt(item -> item.getUnitPrice() * item.getUnits())
                    .sum();

            if (amount != itemsTotal) {
                message.append("Amount doesn't match ItemsTotal. ");
            }
        }

        if (stamp == null || stamp.isEmpty()) {
            isValid = false;
            message.append("Stamp can't be null or empty. ");
        } else if (stamp.length() > 200) {
            isValid = false;
            message.append("Stamp is more than 200 characters. ");
        }

        if (reference == null || reference.isEmpty()) {
            isValid = false;
            message.append("Reference can't be null. ");
        } else if (reference.length() > 200) {
            isValid = false;
            message.append("Reference is more than 200 characters. ");
        }

        if (currency == null || currency.isEmpty()) {
            isValid = false;
            message.append("Currency can't be null. ");
        } else if (!Arrays.asList(supportedCurrencies).contains(currency)) {
            isValid = false;
            message.append("Unsupported currency chosen. ");
        }

        if (language == null || language.isEmpty()) {
            isValid = false;
            message.append("Currency can't be null. ");
        } else if (!Arrays.asList(supportedLanguages).contains(language)) {
            isValid = false;
            message.append("Unsupported language chosen. ");
        }

        if (customer == null) {
            isValid = false;
            message.append("Customer can't be null. ");
        } else {
            ValidationResult customerValidationResult = customer.validate();
            if (!customerValidationResult.isValid()) {
                isValid = false;
                message.append(customerValidationResult.getMessage());
            }
        }

        if (redirectUrls == null) {
            isValid = false;
            message.append("Object redirectUrls can't be null. ");
        } else {
            ValidationResult redirectUrlsValidationResult = redirectUrls.validate();
            if (!redirectUrlsValidationResult.isValid()) {
                isValid = false;
                message.append(redirectUrlsValidationResult.getMessage());
            }
        }

        if (callbackUrls != null) {
            ValidationResult callbackUrlsValidationResult = callbackUrls.validate();
            if (!callbackUrlsValidationResult.isValid()) {
                isValid = false;
                message.append(callbackUrlsValidationResult.getMessage());
            }
        }

        if (deliveryAddress != null) {
            ValidationResult deliveryAddressValidationResult = deliveryAddress.validate();
            if (!deliveryAddressValidationResult.isValid()) {
                isValid = false;
                message.append(deliveryAddressValidationResult.getMessage());
            }
        }

        if (invoicingAddress != null) {
            ValidationResult invoicingAddressValidationResult = invoicingAddress.validate();
            if (!invoicingAddressValidationResult.isValid()) {
                isValid = false;
                message.append(invoicingAddressValidationResult.getMessage());
            }
        }

        if (groups != null) {
            Set<String> allowedGroups = EnumSet.allOf(PaymentMethodGroup.class).stream()
                    .map(Enum::toString)
                    .collect(Collectors.toSet());

            for (String group : groups) {
                if (!allowedGroups.contains(group)) {
                    isValid = false;
                    message.append(" value ").append(group).append(" is not in the list of payment methods");
                }
            }
        }

        return new ValidationResult(isValid, message);
    }
}

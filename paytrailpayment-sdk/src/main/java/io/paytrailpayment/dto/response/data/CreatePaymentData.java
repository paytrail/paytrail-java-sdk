package io.paytrailpayment.dto.response.data;

import io.paytrailpayment.dto.response.model.ApplePay;
import io.paytrailpayment.dto.response.model.PaymentMethodGroupData;
import io.paytrailpayment.dto.response.model.Provider;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePaymentData {
    /**
     * Assigned transaction ID for the payment.
     */
    private String transactionId;

    /**
     * URL to hosted payment gateway.
     * Redirect (HTTP GET) user here if the payment forms cannot be rendered directly inside the web shop.
     */
    private String href;

    /**
     * The bank reference used for the payments.
     */
    private String reference;

    /**
     * Localized text with a link to the terms of payment.
     */
    private String terms;

    /**
     * Array of payment method group data with localized names and URLs to icons.
     */
    private List<PaymentMethodGroupData> groups;

    /**
     * Array of providers. Render these elements as HTML forms
     */
    private List<Provider> providers;

    /**
     * Providers which require custom implementation. Currently used only by Apple Pay.
     */
    private Map<String, ApplePay> customProviders;
}
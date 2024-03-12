package io.paytrailpayment.dto.response.model;

import io.paytrailpayment.dto.request.model.PaymentMethodGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Provider {
    /**
     * Form target URL. Use POST as method.
     */
    private String url;

    /**
     * URL to PNG version of the provider icon.
     */
    private String icon;

    /**
     * URL to SVG version of the provider icon. Using the SVG icon is preferred.
     */
    private String svg;

    /**
     * Display name of the provider.
     */
    private String name;

    /**
     * Provider group. Provider groups allow presenting same type of providers in separate
     * groups which usually makes it easier for the customer to select a payment method.
     */
    private PaymentMethodGroup group;

    /**
     * ID of the provider.
     */
    private String id;

    /**
     * Array of form fields.
     */
    private FormField[] parameters;
}

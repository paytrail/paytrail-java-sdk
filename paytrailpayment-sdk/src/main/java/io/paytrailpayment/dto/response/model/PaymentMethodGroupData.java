package io.paytrailpayment.dto.response.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentMethodGroupData {
    /**
     * ID of the group.
     */
    private String id;

    /**
     * Localized name of the group.
     */
    private String name;

    /**
     * URL to PNG version of the group icon.
     */
    private String icon;

    /**
     * URL to SVG version of the group icon. Using the SVG icon is preferred.
     */
    private String svg;
}

package io.paytrailpayment.dto.response.model;

import io.paytrailpayment.dto.request.model.PaytrailPaymentMethodGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplePay {
    /**
     * Id.
     */
    private String id;

    /**
     * Name.
     */
    private String name;

    /**
     * Group.
     */
    private PaytrailPaymentMethodGroup group;

    /**
     * Parameters.
     */
    private List<FormField> parameters;
}

package io.paytrailpayment.dto.response.model;

import io.paytrailpayment.dto.request.model.PaytrailPaymentMethodGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentMethodGroupDataWithProviders {
    private PaytrailPaymentMethodGroup id;
    private String name;
    private String icon;
    private String svg;
    private List<Provider> providers;
}
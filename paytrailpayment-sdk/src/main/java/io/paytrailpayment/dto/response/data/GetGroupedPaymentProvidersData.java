package io.paytrailpayment.dto.response.data;

import io.paytrailpayment.dto.response.model.PaymentMethodGroupDataWithProviders;
import io.paytrailpayment.dto.response.model.Provider;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetGroupedPaymentProvidersData {
    private String terms;
    private List<PaymentMethodGroupDataWithProviders> groups;
    private List<Provider> providers;
}
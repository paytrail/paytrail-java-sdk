package io.paytrailpayment.dto.response;

import io.paytrailpayment.dto.response.data.GetGroupedPaymentProvidersData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetGroupedPaymentProvidersResponse extends Response {
    private GetGroupedPaymentProvidersData data;
}
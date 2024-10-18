package io.paytrailpayment.dto.response.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateMitPaymentChargeData {
    private String transactionId;
    private String threeDSecureUrl;
}

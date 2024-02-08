package io.paytrailpayment.dto.response;

import io.paytrailpayment.dto.response.data.GetPaymentData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetPaymentResponse extends Response {
    private GetPaymentData data;
}

package io.paytrailpayment.dto.response.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateRefundData {
    /**
     * Assigned transaction ID for the payment
     */
    private String transactionId;

    /**
     * Provider id for the original payment.
     */
    private String provider;

    /**
     * pending, ok, or fail. Status pending indicates that the refund request has been received, but not completed.
     */
    private String status;
}

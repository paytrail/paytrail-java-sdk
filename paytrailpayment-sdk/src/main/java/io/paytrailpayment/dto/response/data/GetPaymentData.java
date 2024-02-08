package io.paytrailpayment.dto.response.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetPaymentData {
    /**
     * Assigned transaction ID for the payment.
     */
    private String transactionId;

    /**
     * new, ok, fail, pending, or delayed.
     */
    private String status;

    /**
     * Total amount of the payment in currency's minor units, e.g. for Euros use cents.
     */
    private int amount;

    /**
     * Currency
     */
    private String currency;

    /**
     * Order reference.
     */
    private String reference;

    /**
     * Assigned transaction ID for the payment.
     */
    private String stamp;

    /**
     * If transaction is in status new, link to the hosted payment gateway.
     */
    private String href;

    /**
     * If processed, the name of the payment method provider.
     */
    private String provider;

    /**
     * If paid, the filing code issued by the payment method provider if any. Some providers do not return the filing code.
     */
    private String filingCode;

    /**
     * Timestamp when the transaction was paid.
     */
    private String paidAt;

    /**
     * If payment is settled, corresponding settlement reference is included.
     */
    private String settlementReference;

    /**
     * Transaction creation timestamp.
     */
    private String createdAt;
}

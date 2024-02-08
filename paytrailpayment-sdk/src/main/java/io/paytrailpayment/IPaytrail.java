package io.paytrailpayment;

import io.paytrailpayment.dto.request.CreatePaymentRequest;
import io.paytrailpayment.dto.response.CreatePaymentResponse;
import io.paytrailpayment.dto.response.GetPaymentResponse;

/**
 * Defines methods for interacting with the Paytrail payment system.
 */
public interface IPaytrail {
    /**
     HTTP POST /payments creates a new open payment and returns a JSON object that includes the available payment methods.
     *          The merchant web shop renders HTML forms from the response objects (see example).
     *          The client browser will submit the form to the payment method provider.
     *          Once the payment has been completed the client browser will return to the merchant provided redirect URL.
     *
     * @param paymentRequest The request object containing payment details.
     * @see <a href="https://docs.paytrail.com/#/?id=create">Paytrail Documentation - Create Payment</a>
     */
    CreatePaymentResponse createPayment(CreatePaymentRequest paymentRequest);

    /**
     HTTP GET /payments/{transactionId} returns payment information.
     *    Get transaction info. Payments are reported primarily via callbacks, and implementations should mainly rely on receiving the info via them. All received payments will be eventually reported.
     *    Note! The transaction id needs to be sent on checkout-transaction-id header as well.
     *
     * @param transactionId The unique identifier for the transaction.
     * @see <a href="https://docs.paytrail.com/#/?id=get">Paytrail Documentation - Get Payment</a>
     */
    GetPaymentResponse getPaymentInfo(String transactionId);
}

package io.paytrailpayment;

import io.paytrailpayment.dto.request.CreatePaymentRequest;
import io.paytrailpayment.dto.request.CreateRefundRequest;
import io.paytrailpayment.dto.request.GetGroupedPaymentProvidersRequest;
import io.paytrailpayment.dto.request.GetPaymentProvidersRequest;
import io.paytrailpayment.dto.response.*;

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

    /**
     * HTTP POST /payments/{transactionId}/refund refunds a payment by transaction ID.
     *
     * @param refundRequest   The request object containing refund details.
     * @param transactionId   The unique identifier for the transaction to refund.
     * @see <a href="https://docs.paytrail.com/#/?id=refund">Paytrail Documentation - Create Refund Request</a>
     */
    CreateRefundResponse createRefundRequest(CreateRefundRequest refundRequest, String transactionId);

    /**
     * HTTP GET /merchants/payment-providers?&amount={Amount}&groups={groupsString} returns a list of available payment providers.
     * @param req
     * @return A list of available payment providers.
     * @see <a href="https://docs.paytrail.com/#/?id=list-providers">Paytrail Documentation - Get Payment Providers</a>
     */
    GetPaymentProvidersResponse getPaymentProviders(GetPaymentProvidersRequest req);

    /**
     * HTTP GET /merchants/grouped-payment-providers?&amount={Amount}&groups={groupsString}&&language={Language} returns a list of available payment providers grouped by payment method group.
     * @param req
     * @return A list of available payment providers grouped by payment method group.
     * @see <a href="https://docs.paytrail.com/#/?id=list-grouped-providers">Paytrail Documentation - Get Grouped Payment Providers</a>
     */
    GetGroupedPaymentProvidersResponse getGroupedPaymentProviders(GetGroupedPaymentProvidersRequest req);
}

package io.paytrailpayment;

import io.paytrailpayment.dto.request.*;
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
     * @return GetPaymentResponse
     * @see <a href="https://docs.paytrail.com/#/?id=get">Paytrail Documentation - Get Payment</a>
     */
    GetPaymentResponse getPaymentInfo(String transactionId);

    /**
     * HTTP POST /payments/{transactionId}/refund refunds a payment by transaction ID.
     *
     * @param refundRequest   The request object containing refund details.
     * @param transactionId   The unique identifier for the transaction to refund.
     * @return CreateRefundResponse
     * @see <a href="https://docs.paytrail.com/#/?id=refund">Paytrail Documentation - Create Refund Request</a>
     */
    CreateRefundResponse createRefundRequest(CreateRefundRequest refundRequest, String transactionId);

    /**
     * HTTP POST /tokenization/addcard-form add card form.
     *
     * @param req   The request object containing add card request
     * @return AddCardFormResponse
     * @see <a href="https://docs.paytrail.com/#/?id=add-card-form">Paytrail Documentation - Create Refund Request</a>
     */
    AddCardFormResponse createAddCardFormRequest(AddCardFormRequest req);

    /**
     * HTTP POST /payments/token/mit/charge creates a new MIT payment charge.
     * @param req
     * @return CreateMitPaymentChargeResponse
     * @see <a href="https://docs.paytrail.com/#/?id=create-authorization-hold-or-charge">Paytrail Documentation  - Create authorization hold or charge
     */
    CreateMitOrCitPaymentResponse createMitPaymentCharge(CreateMitOrCitPaymentRequest req);
    /**
     * HTTP POST /payments/token/mit/authorization-hold creates a new MIT authorization hold.
     * @param createMitPaymentAuthorizationHold
     * @return CreateMitPaymentChargeResponse
     * @see <a href="https://docs.paytrail.com/#/?id=create-authorization-hold-or-charge">Paytrail Documentation  - Create authorization hold or charge
     */
    CreateMitOrCitPaymentResponse createMitPaymentAuthorizationHold(CreateMitOrCitPaymentRequest createMitPaymentAuthorizationHold);

    /**
     * HTTP POST /payments/{transactionId}/token/commit commits a MIT payment.
     * @param req
     * @param transactionId
     * @return CreateMitOrCitPaymentResponse
     * @see <a href="https://docs.paytrail.com/#/?id=commit-authorization-hold">Paytrail Documentation  - Create MIT payment commit
     */
    CreateMitOrCitPaymentResponse createMitPaymentCommit(CreateMitOrCitPaymentRequest req, String transactionId);

    /**
     * HTTP POST /payments/token/cit/charge creates a new CIT payment charge.
     * @param req
     * @return CreateMitOrCitPaymentResponse
     * @see <a href="https://docs.paytrail.com/#/?id=create-authorization-hold-or-charge">Paytrail Documentation  - Create authorization hold or charge
     */
    CreateMitOrCitPaymentResponse createCitPaymentCharge(CreateMitOrCitPaymentRequest req);


    /**
     * HTTP POST /payments/token/cit/authorization-hold creates a new CIT authorization hold.
     * @param req
     * @return CreateMitOrCitPaymentResponse
     * @see <a href="https://docs.paytrail.com/#/?id=create-authorization-hold-or-charge">Paytrail Documentation  - Create CIT authorization hold
     */
    CreateMitOrCitPaymentResponse createCitPaymentAuthorizationHold(CreateMitOrCitPaymentRequest req);

    /**
     * HTTP POST /payments/{transactionId}/token/commit commits a CIT payment.
     * @param req
     * @param transactionId
     * @return CreateMitOrCitPaymentResponse
     * @see <a href="https://docs.paytrail.com/#/?id=commit-authorization-hold">Paytrail Documentation  - Create CIT payment commit
     */
    CreateMitOrCitPaymentResponse createCitPaymentCommit(CreateMitOrCitPaymentRequest req, String transactionId);

    /**
     * HTTP POST /tokenization/pay-and-add-card pay and add card.
     * @param req
     * @return
     */
    PayAddCardResponse payAndAddCard(PayAddCardRequest req);

  /**
   *
   * HTTP POST /tokenization/get-token get token.
   * @param req
   * @return the actual card token which can then be used to make payments on the card
   */
  GetTokenResponse createGetTokenRequest(GetTokenRequest req);
}

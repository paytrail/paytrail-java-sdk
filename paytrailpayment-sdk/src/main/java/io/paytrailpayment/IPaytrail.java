package io.paytrailpayment;

import io.paytrailpayment.dto.request.CreatePaymentRequest;
import io.paytrailpayment.dto.response.CreatePaymentResponse;

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
}

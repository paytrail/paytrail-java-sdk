package io.paytrailpayment.exception;

public class PaytrailCommunicationException extends PaytrailBaseException {

    public PaytrailCommunicationException(int errorCode, String message) {
        super(errorCode, message);
    }

    public PaytrailCommunicationException(int errorCode, String message, Throwable cause) {
        super(errorCode, message, cause);
    }
}
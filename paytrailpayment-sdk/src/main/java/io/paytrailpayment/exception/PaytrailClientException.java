package io.paytrailpayment.exception;

import lombok.Getter;

@Getter
public class PaytrailClientException extends PaytrailBaseException {

    public PaytrailClientException(int errorCode, String message) {
        super(errorCode, message);
    }
    public PaytrailClientException(int errorCode, String message, Throwable cause) {
        super(errorCode, message, cause);
    }
}
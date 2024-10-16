package io.paytrailpayment.exception;

import lombok.Getter;

public class PaytrailBaseException extends RuntimeException {
    private final int errorCode;

    public PaytrailBaseException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
    public PaytrailBaseException(int errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }
    public int getErrorCode() {
        return errorCode;
    }
}

package io.paytrailpayment.dto.request;

public abstract class Request {
    protected abstract ValidationResult specificValidate();

    public ValidationResult validate() {
        ValidationResult specificResult = specificValidate();
        // Add common validation logic if needed
        return specificResult;
    }
}

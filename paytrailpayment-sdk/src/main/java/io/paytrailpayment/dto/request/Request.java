package io.paytrailpayment.dto.request;

import java.util.HashMap;
import java.util.Map;

public abstract class Request {
    private final Map<String, String> validationErrors = new HashMap<>();
    protected abstract void specificValidate();

    public ValidationResult validate() {
        specificValidate();
        if (validationErrors.isEmpty()) {
            return new ValidationResult(true, null); // Assuming success
        } else {
            return new ValidationResult(false, validationErrors);
        }
    }
    protected void addValidationError(String field, String message) {
        validationErrors.put(field, message);
    }
}

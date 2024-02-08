package io.paytrailpayment.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ValidationResult {
    private final boolean isValid;
    private final StringBuilder message;

    public ValidationResult(boolean isValid, StringBuilder message) {
        this.isValid = isValid;
        this.message = message;
    }

    public void addMessage(String msg) {
        if (message.length() > 0) {
            message.append(" ");
        }
        message.append(msg);
    }

    public boolean isValid() {
        return isValid;
    }
}
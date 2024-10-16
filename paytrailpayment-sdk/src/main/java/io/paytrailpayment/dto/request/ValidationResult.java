package io.paytrailpayment.dto.request;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter @Setter
public class ValidationResult {
    private final boolean isValid;
    private Map<String, String> messages;
    private static final Gson gson = new Gson();

    public ValidationResult(boolean isValid, Map<String, String> messages) {
        this.isValid = isValid;
        this.messages = messages;
    }

    public boolean isValid() {
        return isValid;
    }

    public String getMessagesAsJson() {
        return gson.toJson(this.messages);
    }
}
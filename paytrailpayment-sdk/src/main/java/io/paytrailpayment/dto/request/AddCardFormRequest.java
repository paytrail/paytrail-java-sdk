package io.paytrailpayment.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Arrays;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class AddCardFormRequest extends Request {
    @JsonProperty("checkout-account")
    private Integer checkoutAccount;
    
    @JsonProperty("checkout-algorithm")
    private String checkoutAlgorithm;
    
    @JsonProperty("checkout-method")
    private String checkoutMethod;
    
    @JsonProperty("checkout-nonce")
    private String checkoutNonce;
    
    @JsonProperty("checkout-timestamp")
    private String checkoutTimestamp;
    
    @JsonProperty("checkout-redirect-success-url")
    private String checkoutRedirectSuccessUrl;
    
    @JsonProperty("checkout-redirect-cancel-url")
    private String checkoutRedirectCancelUrl;
    
    @JsonProperty("signature")
    private String signature;
    
    @JsonProperty("checkout-callback-success-url")
    private String checkoutCallbackSuccessUrl;
    
    @JsonProperty("checkout-callback-cancel-url")
    private String checkoutCallbackCancelUrl;
    
    @JsonProperty("language")
    private String language;

    @Override
    protected void specificValidate() {
        String[] supportedLanguages = {"FI", "SV", "EN"};
        String[] supportedMethods = {"GET", "POST"};

        if (checkoutAccount == null) {
            addValidationError("checkout-account", "checkout-account is empty.");
        }

        if (checkoutAlgorithm == null || checkoutAlgorithm.isEmpty()) {
            addValidationError("checkout-algorithm", "checkout-algorithm is empty.");
        }

        if (!Arrays.asList(supportedMethods).contains(checkoutMethod)) {
            addValidationError("checkout-method", "unsupported method chosen.");
        }

        if (checkoutTimestamp == null || checkoutTimestamp.isEmpty()) {
            addValidationError("checkout-timestamp", "checkout-timestamp is empty.");
        }

        if (checkoutRedirectSuccessUrl == null || checkoutRedirectSuccessUrl.isEmpty()) {
            addValidationError("checkout-redirect-success-url", "checkout-redirect success url is empty.");
        }

        if (checkoutRedirectCancelUrl == null || checkoutRedirectCancelUrl.isEmpty()) {
            addValidationError("checkout-redirect-cancel-url", "checkout-redirect cancel url is empty.");
        }

        if (!Arrays.asList(supportedLanguages).contains(language)) {
            addValidationError("language", "unsupported language chosen.");
        }
    }
}
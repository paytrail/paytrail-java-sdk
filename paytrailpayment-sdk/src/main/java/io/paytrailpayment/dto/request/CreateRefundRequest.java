package io.paytrailpayment.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.paytrailpayment.dto.request.model.CallbackUrl;
import io.paytrailpayment.dto.request.model.RefundItem;
import io.paytrailpayment.utilites.Constants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class CreateRefundRequest extends Request {
    /**
     * Total amount to refund, in currency's minor units. Required for normal payment refunds.
     */
    private int amount;

    /**
     * Refund recipient email address.
     */
    private String email;

    /**
     * Merchant unique identifier for the refund.
     */
    private String refundStamp;

    /**
     * Refund reference.
     */
    private String refundReference;

    /**
     * Array of items to refund.
     */
    private List<RefundItem> items;

    /**
     * Which urls to ping after the refund has been processed.
     */
    private CallbackUrl callbackUrls;

    @Override()
    protected void specificValidate() {
        if (items != null && !items.isEmpty()) {
            for (RefundItem item : items) {
                ValidationResult itemValidationResult = item.validate();
                if (!itemValidationResult.isValid()) {
                    addValidationError("items", itemValidationResult.getMessagesAsJson());
                    break;
                }
            }
        }

        // Validate email using Regex
        if (email != null && !email.isEmpty()) {
            Pattern emailPattern = Pattern.compile(Constants.EMAIL_REGEX);
            Matcher emailMatcher = emailPattern.matcher(email);
            if (!emailMatcher.matches()) {
                addValidationError("email", "Email is not a valid email address.");
            }
        } else {
            addValidationError("email", "Email cannot be null or empty.");
        }

        // Validate callbackUrls
        if (callbackUrls == null) {
            addValidationError("callbackUrls", "Callback Urls can't be null.");
        } else {
            ValidationResult callbackUrlsValidationResult = callbackUrls.validate();
            if (!callbackUrlsValidationResult.isValid()) {
                addValidationError("callbackUrls", callbackUrlsValidationResult.getMessagesAsJson());
            }
        }
    }
}

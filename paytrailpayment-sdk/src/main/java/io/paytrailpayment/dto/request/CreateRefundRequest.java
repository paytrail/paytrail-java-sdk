package io.paytrailpayment.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.paytrailpayment.dto.request.model.CallbackUrl;
import io.paytrailpayment.dto.request.model.RefundItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class CreateRefundRequest extends Request {
    /**
     * Total amount to refund, in currency's minor units (ie. EUR cents). Required for normal payment refunds.
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
    protected ValidationResult specificValidate() {
        StringBuilder message = new StringBuilder();
        boolean isValid = true;

        if (items != null) {
            for (RefundItem item : items) {
                ValidationResult itemValidationResult = item.validate();
                if (!itemValidationResult.isValid()) {
                    isValid = false;
                    message.append(itemValidationResult.getMessage());
                    break;
                }
            }
        }

        if (callbackUrls == null) {
            isValid = false;
            message.append("Object callbackUrls can't be null. ");
        } else {
            ValidationResult callbackUrlsValidationResult = callbackUrls.validate();
            if (!callbackUrlsValidationResult.isValid()) {
                isValid = false;
                message.append(callbackUrlsValidationResult.getMessage());
            }
        }

        return new ValidationResult(isValid, message);
    }
}

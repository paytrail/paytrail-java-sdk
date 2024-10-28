package io.paytrailpayment.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.paytrailpayment.dto.request.model.PaytrailPaymentMethodGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.StringJoiner;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class GetPaymentProvidersRequest extends Request {
    private int amount;
    private List<PaytrailPaymentMethodGroup> groups;

    @Override
    protected void specificValidate() {
        if (amount <= 0) {
            addValidationError("amount", "Amount cannot be less than or equal to 0.");
        }

        if (groups == null || groups.isEmpty()) {
            addValidationError("groups", "Groups cannot be null or empty.");
        }
    }

    @Override
    public String toString() {
        StringBuilder query = new StringBuilder();

        if (amount > 0) {
            query.append("&amount=").append(amount);
        }

        if (groups != null && !groups.isEmpty()) {
            StringJoiner groupsString = new StringJoiner(",");
            for (PaytrailPaymentMethodGroup group : groups) {
                groupsString.add(group.toString().toLowerCase());
            }
            query.append("&groups=").append(groupsString);
        }

        return query.toString();
    }
}
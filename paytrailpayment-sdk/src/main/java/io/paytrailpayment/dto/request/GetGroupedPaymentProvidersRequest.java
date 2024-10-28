package io.paytrailpayment.dto.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class GetGroupedPaymentProvidersRequest extends GetPaymentProvidersRequest {
    private String language;

    @Override
    protected void specificValidate() {
        super.specificValidate();
    }

    @Override
    public String toString() {
        String query = super.toString();
        if (language != null && !language.isEmpty()) {
            query += "&language=" + language;
        }
        return query;
    }
}
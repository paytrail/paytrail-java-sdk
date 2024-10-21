package io.paytrailpayment.dto.response;

import io.paytrailpayment.dto.response.data.PayAddCardData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PayAddCardResponse extends Response {
    private PayAddCardData data;

    public PayAddCardResponse(int returnCode, String returnMessage, PayAddCardData data) {
        super(returnCode, returnMessage);
        this.data = data;
    }
}

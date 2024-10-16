package io.paytrailpayment.dto.request.model;

import lombok.Getter;

@Getter
public enum PaytrailCurrency {
    EUR("EUR");

    private final String currency;
    PaytrailCurrency(String currency) {
        this.currency = currency;
    }
}

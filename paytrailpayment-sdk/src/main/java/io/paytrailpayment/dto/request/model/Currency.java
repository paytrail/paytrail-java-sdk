package io.paytrailpayment.dto.request.model;

import lombok.Getter;

@Getter
public enum Currency {
    EUR("EUR");

    private final String currency;
    Currency(String currency) {
        this.currency = currency;
    }
}

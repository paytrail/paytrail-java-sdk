package io.paytrailpayment.dto.request.model;

import lombok.Getter;

@Getter
public enum PaytrailLanguage {
    FI("FI"),
    SV("SV"),
    EN("EN");

    private final String language;
    PaytrailLanguage(String language) {
        this.language = language;
    }
}

package io.paytrailpayment.dto.request.model;

import lombok.Getter;

@Getter
public enum Language {
    FI("FI"),
    SV("SV"),
    EN("EN");

    private final String language;
    Language(String language) {
        this.language = language;
    }
}

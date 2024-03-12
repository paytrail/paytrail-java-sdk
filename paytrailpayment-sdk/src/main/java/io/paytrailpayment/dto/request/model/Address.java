package io.paytrailpayment.dto.request.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.paytrailpayment.dto.request.Request;
import io.paytrailpayment.dto.request.ValidationResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class Address extends Request {
    /**
     * Street address. Maximum of 50 characters.
     */
    private String streetAddress;

    /**
     * Postal code. Maximum of 15 characters.
     */
    private String postalCode;

    /**
     * City. maximum of 30 characters.
     */
    private String city;

    /**
     * County/State
     */
    private String county;

    /**
     * Alpha-2 country code
     */
    private String country;

    @Override
    protected ValidationResult specificValidate() {
        boolean isValid = true;
        StringBuilder message = new StringBuilder();

        if (streetAddress == null || streetAddress.isEmpty()) {
            message.append("Address's streetAddress can't be null. ");
            isValid = false;
        } else if (streetAddress.length() > 50) {
            message.append("Address's streetAddress is more than 50 characters. ");
            isValid = false;
        }

        if (postalCode == null || postalCode.isEmpty()) {
            message.append("Address's postalCode can't be null. ");
            isValid = false;
        } else if (postalCode.length() > 15) {
            message.append("Address's postalCode is more than 15 characters. ");
            isValid = false;
        }

        if (city == null || city.isEmpty()) {
            message.append("Address's city can't be null. ");
            isValid = false;
        } else if (city.length() > 30) {
            message.append("Address's city is more than 30 characters. ");
            isValid = false;
        }

        if (country == null || country.isEmpty()) {
            message.append("Address's country can't be null. ");
            isValid = false;
        }

        return new ValidationResult(isValid, message);
    }
}

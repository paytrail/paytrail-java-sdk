package io.paytrailpayment.dto.request.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.paytrailpayment.dto.request.Request;
import io.paytrailpayment.dto.request.ValidationResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

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
    protected void specificValidate() {
        if (StringUtils.isBlank(streetAddress)) {
            addValidationError("streetAddress", "Address's streetAddress can't be null or empty.");
        } else if (streetAddress.length() > 50) {
            addValidationError("streetAddress", "Address's streetAddress is more than 50 characters.");
        }

        if (StringUtils.isBlank(postalCode)) {
            addValidationError("postalCode", "Address's postalCode can't be null or empty.");
        } else if (postalCode.length() > 15) {
            addValidationError("postalCode", "Address's postalCode is more than 15 characters.");
        }

        if (StringUtils.isBlank(city)) {
            addValidationError("city", "Address's city can't be null or empty.");
        } else if (city.length() > 30) {
            addValidationError("city", "Address's city is more than 30 characters.");
        }

        if (StringUtils.isBlank(country)) {
            addValidationError("country", "Address's country can't be null or empty.");
        }

        if (StringUtils.isNotBlank(county) && county.length() > 200) {
            addValidationError("county", "Address's county is more than 200 characters.");
        }
    }
}

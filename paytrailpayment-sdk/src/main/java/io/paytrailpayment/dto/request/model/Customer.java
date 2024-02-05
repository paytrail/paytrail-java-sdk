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
public class Customer extends Request {
    /**
     * Email. Maximum of 200 characters.
     */
    private String email;

    /**
     * First name (required for OPLasku and Walley/Collector). Maximum of 50 characters.
     */
    private String firstName;

    /**
     * Last name (required for OPLasku and Walley/Collector). Maximum of 50 characters.
     */
    private String lastName;

    /**
     * Phone number.
     */
    private String phone;

    /**
     * VAT ID, if any.
     */
    private String vatId;

    /**
     * Company name, if any.
     */
    private String companyName;

    @Override()
    protected ValidationResult specificValidate() {
        boolean isValid = true;
        StringBuilder message = new StringBuilder();

        if (email == null) {
            isValid = false;
            message.append("Customer's email can't be null. ");
        } else if (email.length() > 200) {
            isValid = false;
            message.append("Customer's email is more than 200 characters. ");
        }

        if (firstName != null && firstName.length() > 50) {
            isValid = false;
            message.append("Customer's firstName is more than 50 characters. ");
        }

        if (lastName != null && lastName.length() > 100) {
            isValid = false;
            message.append("Customer's lastName is more than 50 characters. ");
        }

        return new ValidationResult(isValid, message);
    }
}


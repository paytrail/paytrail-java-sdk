package io.paytrailpayment.dto.request.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.paytrailpayment.dto.request.Request;
import io.paytrailpayment.dto.request.ValidationResult;
import io.paytrailpayment.utilites.Constants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    protected void specificValidate() {
        if (Objects.isNull(email)) {
            addValidationError("email", "Customer's email can't be null.");
        } else if (!Pattern.compile(Constants.EMAIL_REGEX).matcher(email).matches()) {
            addValidationError("email", "Customer's email is not a valid email address.");
        } else if (email.length() > 200) {
            addValidationError("email", "Customer's email is more than 200 characters.");
        }

        if (Objects.nonNull(firstName) && firstName.length() > 50) {
            addValidationError("firstName", "Customer's firstName is more than 50 characters.");
        }

        if (Objects.nonNull(lastName) && lastName.length() > 50) {
            addValidationError("lastName", "Customer's lastName is more than 50 characters.");
        }

        if (Objects.nonNull(companyName) && companyName.length() > 100) {
            addValidationError("companyName", "Customer's companyName is more than 100 characters.");
        }
    }
}


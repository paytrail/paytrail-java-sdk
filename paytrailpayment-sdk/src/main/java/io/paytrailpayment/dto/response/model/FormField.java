package io.paytrailpayment.dto.response.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FormField {
    /**
     * Name of the input.
     */
    private String name;

    /**
     * Value of the input.
     */
    private String value;
}

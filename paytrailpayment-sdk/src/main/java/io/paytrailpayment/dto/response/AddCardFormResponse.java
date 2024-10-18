package io.paytrailpayment.dto.response;

import io.paytrailpayment.dto.response.data.AddCardFormData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddCardFormResponse extends Response {
    private AddCardFormData data;

    public AddCardFormResponse(int statusCode, String description, AddCardFormData dataMapper) {
        super(statusCode, description);
        this.data = dataMapper;
    }
}

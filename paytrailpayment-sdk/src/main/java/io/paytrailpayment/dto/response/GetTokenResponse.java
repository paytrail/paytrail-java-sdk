package io.paytrailpayment.dto.response;

import io.paytrailpayment.dto.response.data.GetTokenData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetTokenResponse extends Response {
    private GetTokenData data;

    public GetTokenResponse(int statusCode, String description, GetTokenData dataMapper) {
        super(statusCode, description);
        this.data = dataMapper;

    }
}

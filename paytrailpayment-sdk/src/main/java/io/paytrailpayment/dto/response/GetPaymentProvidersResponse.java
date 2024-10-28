package io.paytrailpayment.dto.response;

import io.paytrailpayment.dto.response.data.GetPaymentProvidersData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetPaymentProvidersResponse extends Response {
    private List<GetPaymentProvidersData> data;

    public GetPaymentProvidersResponse(int statusCode, String description, List<GetPaymentProvidersData> dataMapper) {
        super(statusCode, description);
        this.data = dataMapper;
    }
}
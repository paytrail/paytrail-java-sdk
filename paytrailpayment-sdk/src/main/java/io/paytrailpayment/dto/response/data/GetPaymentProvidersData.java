package io.paytrailpayment.dto.response.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetPaymentProvidersData {
    private String id;
    private String name;
    private String icon;
    private String svg;
    private String group;
}
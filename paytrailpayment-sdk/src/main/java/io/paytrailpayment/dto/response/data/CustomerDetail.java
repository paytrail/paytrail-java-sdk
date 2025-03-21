package io.paytrailpayment.dto.response.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDetail {
  /**
   * Customer's network address.
   */
  private String networkAddress;

  /**
   * Country code.
   */
  private String countryCode;

}

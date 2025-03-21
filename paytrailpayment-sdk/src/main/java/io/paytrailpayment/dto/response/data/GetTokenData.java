package io.paytrailpayment.dto.response.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetTokenData {
  /**
   * Payment card token.
   */
  private String token;
  /**
   * Masked card details. Present if verification was successful.
   */
  private Card card;
  /**
   * Customer details.
   */
  private CustomerDetail customer;
}

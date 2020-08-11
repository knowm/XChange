package org.knowm.xchange.independentreserve.dto.account;

import org.knowm.xchange.independentreserve.dto.auth.AuthAggregate;

public class IndependentReserveDepositAddressRequest extends AuthAggregate {

  public IndependentReserveDepositAddressRequest(
      String apiKey, Long nonce, String primaryCurrencyCode) {
    super(apiKey, nonce);
    this.parameters.put("primaryCurrencyCode", primaryCurrencyCode);
  }
}

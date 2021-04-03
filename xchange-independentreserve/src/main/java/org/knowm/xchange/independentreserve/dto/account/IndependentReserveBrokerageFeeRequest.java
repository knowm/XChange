package org.knowm.xchange.independentreserve.dto.account;

import org.knowm.xchange.independentreserve.dto.auth.AuthAggregate;

public class IndependentReserveBrokerageFeeRequest extends AuthAggregate {

  public IndependentReserveBrokerageFeeRequest(String apiKey, Long nonce) {
    super(apiKey, nonce);
  }
}

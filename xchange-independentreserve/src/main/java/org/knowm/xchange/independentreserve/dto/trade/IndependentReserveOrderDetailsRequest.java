package org.knowm.xchange.independentreserve.dto.trade;

import org.knowm.xchange.independentreserve.dto.auth.AuthAggregate;

public class IndependentReserveOrderDetailsRequest extends AuthAggregate {

  public IndependentReserveOrderDetailsRequest(String apiKey, Long nonce, String orderGuid) {
    super(apiKey, nonce);
    this.parameters.put("orderGuid", orderGuid);
  }
}

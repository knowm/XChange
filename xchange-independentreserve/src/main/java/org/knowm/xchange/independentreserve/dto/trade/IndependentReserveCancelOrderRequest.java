package org.knowm.xchange.independentreserve.dto.trade;

import org.knowm.xchange.independentreserve.dto.auth.AuthAggregate;

/** Author: Kamil Zbikowski Date: 4/15/15 */
public class IndependentReserveCancelOrderRequest extends AuthAggregate {

  public IndependentReserveCancelOrderRequest(String apiKey, Long nonce, String orderGuid) {
    super(apiKey, nonce);
    this.parameters.put("orderGuid", orderGuid);
  }
}

package org.knowm.xchange.independentreserve.dto.trade;

import org.knowm.xchange.independentreserve.dto.auth.AuthAggregate;

public class IndependentReservePlaceMarketOrderRequest extends AuthAggregate {

  public IndependentReservePlaceMarketOrderRequest(
      String apiKey,
      Long nonce,
      String primaryCurrencyCode,
      String secondaryCurrencyCode,
      String orderType,
      String volume) {
    super(apiKey, nonce);
    this.parameters.put("primaryCurrencyCode", primaryCurrencyCode);
    this.parameters.put("secondaryCurrencyCode", secondaryCurrencyCode);
    this.parameters.put("orderType", orderType);
    this.parameters.put("volume", volume);
  }
}

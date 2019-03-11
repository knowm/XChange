package org.knowm.xchange.okcoin.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OkCoinFuturesOrderResult extends OkCoinErrorResult {

  private final OkCoinFuturesOrder[] orders;

  public OkCoinFuturesOrderResult(
      @JsonProperty("result") final boolean result,
      @JsonProperty("error_code") final int errorCode,
      @JsonProperty("orders") final OkCoinFuturesOrder[] orders) {

    super(result, errorCode);
    this.orders = orders;
  }

  public OkCoinFuturesOrder[] getOrders() {

    return orders;
  }
}

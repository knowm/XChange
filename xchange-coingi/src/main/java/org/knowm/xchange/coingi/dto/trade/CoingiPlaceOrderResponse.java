package org.knowm.xchange.coingi.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class CoingiPlaceOrderResponse {
  /** { "result": "11e6e96e-0677-8a94-8609-0059bb86f908" } */
  private String result;

  public CoingiPlaceOrderResponse(@JsonProperty("result") String result) {
    this.result = result;
  }

  @Override
  public String toString() {
    return String.format("CoingiOrder{result=%s}", result);
  }

  public String getResult() {
    return result;
  }
}

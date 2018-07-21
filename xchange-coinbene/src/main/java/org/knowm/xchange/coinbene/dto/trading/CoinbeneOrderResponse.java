package org.knowm.xchange.coinbene.dto.trading;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.coinbene.dto.CoinbeneResponse;

public class CoinbeneOrderResponse extends CoinbeneResponse {

  private final String orderId;

  public CoinbeneOrderResponse(@JsonProperty("orderid") String orderId) {
    this.orderId = orderId;
  }

  public String getOrderId() {
    return orderId;
  }
}

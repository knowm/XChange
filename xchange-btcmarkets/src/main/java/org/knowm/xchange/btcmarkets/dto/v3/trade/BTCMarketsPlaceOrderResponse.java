package org.knowm.xchange.btcmarkets.dto.v3.trade;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BTCMarketsPlaceOrderResponse {

  public final String orderId;

  public BTCMarketsPlaceOrderResponse(@JsonProperty("orderId") String orderId) {
    this.orderId = orderId;
  }
}

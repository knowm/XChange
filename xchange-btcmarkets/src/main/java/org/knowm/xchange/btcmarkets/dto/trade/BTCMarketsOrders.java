package org.knowm.xchange.btcmarkets.dto.trade;

import java.util.List;

import org.knowm.xchange.btcmarkets.dto.BTCMarketsBaseResponse;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BTCMarketsOrders extends BTCMarketsBaseResponse {
  protected BTCMarketsOrders(@JsonProperty("success") Boolean success, @JsonProperty("errorMessage") String errorMessage,
      @JsonProperty("errorCode") Integer errorCode, @JsonProperty("orders") List<BTCMarketsOrder> orders) {
    super(success, errorMessage, errorCode);
    this.orders = orders;
  }

  private List<BTCMarketsOrder> orders;

  public List<BTCMarketsOrder> getOrders() {
    return orders;
  }
}

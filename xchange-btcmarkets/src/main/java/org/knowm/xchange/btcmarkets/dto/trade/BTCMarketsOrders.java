package org.knowm.xchange.btcmarkets.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import org.knowm.xchange.btcmarkets.dto.BTCMarketsBaseResponse;

public class BTCMarketsOrders extends BTCMarketsBaseResponse {
  private List<BTCMarketsOrder> orders;

  public BTCMarketsOrders(
      @JsonProperty("success") Boolean success,
      @JsonProperty("errorMessage") String errorMessage,
      @JsonProperty("errorCode") Integer errorCode,
      @JsonProperty("orders") List<BTCMarketsOrder> orders) {
    super(success, errorMessage, errorCode);
    this.orders = orders;
  }

  public List<BTCMarketsOrder> getOrders() {
    return orders;
  }
}

package org.knowm.xchange.bithumb.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.bithumb.dto.BithumbResponse;

public class BithumbTradeResponse extends BithumbResponse {

  private final String orderId;

  public BithumbTradeResponse(
      @JsonProperty("status") String status,
      @JsonProperty("message") String message,
      @JsonProperty("order_id") String orderId) {
    super(status, message, null);
    this.orderId = orderId;
  }

  public String getOrderId() {
    return orderId;
  }
}

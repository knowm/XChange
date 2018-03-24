package org.knowm.xchange.quoine.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

public class QuoineNewOrderRequestWrapper {

  @JsonProperty("order")
  private final QuoineNewOrderRequest order;

  public QuoineNewOrderRequestWrapper(QuoineNewOrderRequest order) {
    this.order = order;
  }

  public QuoineNewOrderRequest getOrder() {
    return order;
  }
}

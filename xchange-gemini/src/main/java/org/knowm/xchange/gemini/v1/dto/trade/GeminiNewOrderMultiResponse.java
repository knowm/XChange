package org.knowm.xchange.gemini.v1.dto.trade;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GeminiNewOrderMultiResponse {

  private final GeminiOrderStatusResponse[] orderStatuses;

  public GeminiNewOrderMultiResponse(@JsonProperty("order_ids") GeminiOrderStatusResponse[] orderStatuses) {
    this.orderStatuses = orderStatuses;
  }

  public GeminiOrderStatusResponse[] getOrderStatuses() {
    return orderStatuses;
  }

  @Override
  public String toString() {
    return "GeminiNewOrderMultiResponse [orderStatuses=" + Arrays.toString(orderStatuses) + "]";
  }

}

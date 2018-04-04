package org.knowm.xchange.bitfinex.v1.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Arrays;

public class BitfinexNewOrderMultiResponse {

  private final BitfinexOrderStatusResponse[] orderStatuses;

  public BitfinexNewOrderMultiResponse(
      @JsonProperty("order_ids") BitfinexOrderStatusResponse[] orderStatuses) {
    this.orderStatuses = orderStatuses;
  }

  public BitfinexOrderStatusResponse[] getOrderStatuses() {
    return orderStatuses;
  }

  @Override
  public String toString() {
    return "BitfinexNewOrderMultiResponse [orderStatuses=" + Arrays.toString(orderStatuses) + "]";
  }
}

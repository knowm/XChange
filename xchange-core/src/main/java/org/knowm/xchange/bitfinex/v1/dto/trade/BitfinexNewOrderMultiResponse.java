package org.knowm.xchange.bitfinex.v1.dto.trade;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BitfinexNewOrderMultiResponse {

  private final BitfinexOrderStatusResponse[] orderStatuses;

  public BitfinexNewOrderMultiResponse(@JsonProperty("order_ids") BitfinexOrderStatusResponse[] orderStatuses) {
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

package org.knowm.xchange.btcmarkets.dto.trade;

import java.util.Arrays;
import java.util.List;

public class BTCMarketsCancelOrderRequest {

  private List<Long> orderIds;

  protected BTCMarketsCancelOrderRequest() {}

  public BTCMarketsCancelOrderRequest(List<Long> orderIds) {
    this.orderIds = orderIds;
  }

  public BTCMarketsCancelOrderRequest(Long... orderIds) {
    this.orderIds = Arrays.asList(orderIds);
  }

  public List<Long> getOrderIds() {
    return orderIds;
  }
}

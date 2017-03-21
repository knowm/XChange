package org.knowm.xchange.gdax.dto.trade;

import org.knowm.xchange.service.trade.params.TradeHistoryParams;

public class GDAXTradeHistoryParams implements TradeHistoryParams {

  String orderId;

  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }
}

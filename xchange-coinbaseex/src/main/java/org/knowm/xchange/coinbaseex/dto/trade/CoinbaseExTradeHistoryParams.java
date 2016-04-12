package org.knowm.xchange.coinbaseex.dto.trade;

import org.knowm.xchange.service.polling.trade.params.TradeHistoryParams;

public class CoinbaseExTradeHistoryParams implements TradeHistoryParams {

  String orderId;

  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }
}

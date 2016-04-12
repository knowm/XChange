package com.xeiam.xchange.coinbaseex.dto.trade;

import com.xeiam.xchange.service.polling.trade.params.TradeHistoryParams;

public class CoinbaseExTradeHistoryParams implements TradeHistoryParams {

  String orderId;

  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }
}

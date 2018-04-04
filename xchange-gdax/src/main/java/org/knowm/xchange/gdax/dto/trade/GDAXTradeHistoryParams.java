package org.knowm.xchange.gdax.dto.trade;

import org.knowm.xchange.service.trade.params.TradeHistoryParamTransactionId;

public class GDAXTradeHistoryParams implements TradeHistoryParamTransactionId {

  String orderId = null;

  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  @Override
  public String getTransactionId() {
    return orderId;
  }

  @Override
  public void setTransactionId(String txId) {
    orderId = txId;
  }
}

package com.xeiam.xchange.service.polling.trade.params;

public interface TradeHistoryParamTransactionId extends TradeHistoryParams {
  void setTransactionId(String txId);

  String getTransactionId();
}

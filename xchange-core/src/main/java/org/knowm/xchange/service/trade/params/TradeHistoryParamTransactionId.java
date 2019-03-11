package org.knowm.xchange.service.trade.params;

public interface TradeHistoryParamTransactionId extends TradeHistoryParams {
  String getTransactionId();

  void setTransactionId(String txId);
}

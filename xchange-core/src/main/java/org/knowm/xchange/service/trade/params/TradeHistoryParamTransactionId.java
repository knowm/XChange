package org.knowm.xchange.service.trade.params;

/**
 * {@link TradeHistoryParams} with id of single trade as param
 */
public interface TradeHistoryParamTransactionId extends TradeHistoryParams {
  String getTransactionId();

  void setTransactionId(String txId);
}

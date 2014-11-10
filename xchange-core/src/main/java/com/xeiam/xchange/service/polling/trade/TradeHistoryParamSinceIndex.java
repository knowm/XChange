package com.xeiam.xchange.service.polling.trade;

public interface TradeHistoryParamSinceIndex extends TradeHistoryParams {
  void setStartIndex(Long from);

  Long getStartIndex();
}

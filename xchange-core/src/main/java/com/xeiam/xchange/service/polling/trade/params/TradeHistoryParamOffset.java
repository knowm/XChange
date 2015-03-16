package com.xeiam.xchange.service.polling.trade.params;

public interface TradeHistoryParamOffset extends TradeHistoryParams {

  void setOffset(Long offset);

  Long getOffset();
}

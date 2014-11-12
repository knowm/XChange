package com.xeiam.xchange.service.polling.trade;

public interface TradeHistoryParamOffset extends TradeHistoryParams {

  void setOffset(Long offset);

  Long getOffset();
}

package com.xeiam.xchange.service.polling.trade;

public interface TradeHistoryParamsIdSpan extends TradeHistoryParams {

  void setStartId(String startId);

  String getStartId();

  void setEndId(String endId);

  String getEndId();
}

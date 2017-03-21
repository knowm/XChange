package org.knowm.xchange.service.trade.params;

public interface TradeHistoryParamsIdSpan extends TradeHistoryParams {

  void setStartId(String startId);

  String getStartId();

  void setEndId(String endId);

  String getEndId();
}

package org.knowm.xchange.service.polling.trade.params;

public interface TradeHistoryParamOffset extends TradeHistoryParams {

  void setOffset(Long offset);

  Long getOffset();
}

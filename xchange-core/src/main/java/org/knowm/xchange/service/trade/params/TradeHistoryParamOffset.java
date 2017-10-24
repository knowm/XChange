package org.knowm.xchange.service.trade.params;

public interface TradeHistoryParamOffset extends TradeHistoryParams {

  void setOffset(Long offset);

  Long getOffset();
}

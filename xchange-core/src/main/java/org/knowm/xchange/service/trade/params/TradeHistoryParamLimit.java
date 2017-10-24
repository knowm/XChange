package org.knowm.xchange.service.trade.params;

public interface TradeHistoryParamLimit extends TradeHistoryParams {

  void setLimit(Integer limit);

  Integer getLimit();
}

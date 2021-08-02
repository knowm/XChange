package org.knowm.xchange.deribit.v2.service;

import org.knowm.xchange.service.trade.params.TradeHistoryParams;

public interface DeribitTradeHistoryParamsOld extends TradeHistoryParams {

  Boolean isIncludeOld();

  void setIncludeOld(Boolean includeOld);
}

package org.knowm.xchange.ripple.service.params;

import org.knowm.xchange.service.trade.params.TradeHistoryParams;

/**
 * Keeps track of and restrict the number of notification and order detail queries that are made for
 * a trade history. Due to there not being a single simple API call to return an account's trade
 * history the number of API queries can spiral out of control. This interface helps prevent that.
 */
public interface RippleTradeHistoryCount extends TradeHistoryParams {

  int DEFAULT_API_CALL_COUNT = 100;
  int DEFAULT_TRADE_COUNT_LIMIT = 10;

  void resetApiCallCount();

  void incrementApiCallCount();

  int getApiCallCount();

  int getApiCallCountLimit();

  void resetTradeCount();

  void incrementTradeCount();

  int getTradeCount();

  int getTradeCountLimit();
}

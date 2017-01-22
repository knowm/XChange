package org.knowm.xchange.ripple.service.params;

import org.knowm.xchange.service.trade.params.TradeHistoryParams;

/**
 * Keeps track of and restrict the number of notification and order detail queries that are made for a trade history. Due to there not being a single
 * simple API call to return an account's trade history the number of API queries can spiral out of control. This interface helps prevent that.
 */
public interface RippleTradeHistoryCount extends TradeHistoryParams {

  public static final int DEFAULT_API_CALL_COUNT = 100;

  public void resetApiCallCount();

  public void incrementApiCallCount();

  public int getApiCallCount();

  public int getApiCallCountLimit();

  public static final int DEFAULT_TRADE_COUNT_LIMIT = 10;

  public void resetTradeCount();

  public void incrementTradeCount();

  public int getTradeCount();

  public int getTradeCountLimit();
}

package com.xeiam.xchange.ripple.service.polling.params;

/**
 * Keeps track of and restrict the number of notification and order detail queries that are made for a trade history. Due to there not being a single
 * simple API call to return an account's trade history the number of API queries can spiral out of control. This interface helps prevent that.
 */
public interface RippleTradeHistoryCount {

  public static final int DEFAULT_API_CALL_COUNT = 100;

  public void resetApiCallCount();
  public void incrementApiCallCount();
  public int getApiCallCount();
  public void setApiCallCountLimit(final int value);
  public int getApiCallCountLimit();

  public static final int DEFAULT_TRADE_COUNT_LIMIT = 10;

  public void resetTradeCount();
  public void incrementTradeCount();
  public int getTradeCount();
  public void setTradeCountLimit(final int value);
  public int getTradeCountLimit();
}

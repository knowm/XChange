package com.xeiam.xchange.service.polling.trade;

/**
 * Parameters type for {@link com.xeiam.xchange.service.polling.PollingTradeService#getTradeHistory(TradeHistoryParams)} with start and end timestamps.
 */
public interface TradeHistoryParamsTimeSpan extends TradeHistoryParams {

  void setStartTime(Long startTime);

  Long getStartTime();

  void setEndTime(Long endTime);

  Long getEndTime();

}

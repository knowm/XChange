package com.xeiam.xchange.service.polling.trade;

import java.util.Date;

/**
 * Parameters type for {@link com.xeiam.xchange.service.polling.PollingTradeService#getTradeHistory(TradeHistoryParams)} with start and end timestamps.
 */
public interface TradeHistoryParamsTimeSpan extends TradeHistoryParams {

  void setStartTime(Date startTime);

  Date getStartTime();

  void setEndTime(Date endTime);

  Date getEndTime();

}

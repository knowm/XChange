package org.knowm.xchange.service.trade.params;

import java.util.Date;

import org.knowm.xchange.service.trade.TradeService;

/**
 * Parameters type for {@link TradeService#getTradeHistory(TradeHistoryParams)} with start and end timestamps.
 */
public interface TradeHistoryParamsTimeSpan extends TradeHistoryParams {

  void setStartTime(Date startTime);

  Date getStartTime();

  void setEndTime(Date endTime);

  Date getEndTime();

}

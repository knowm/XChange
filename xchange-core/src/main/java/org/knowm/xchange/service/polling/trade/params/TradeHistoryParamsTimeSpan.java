package org.knowm.xchange.service.polling.trade.params;

import java.util.Date;

/**
 * Parameters type for {@link org.knowm.xchange.service.polling.trade.PollingTradeService#getTradeHistory(TradeHistoryParams)} with start and end
 * timestamps.
 */
public interface TradeHistoryParamsTimeSpan extends TradeHistoryParams {

  void setStartTime(Date startTime);

  Date getStartTime();

  void setEndTime(Date endTime);

  Date getEndTime();

}

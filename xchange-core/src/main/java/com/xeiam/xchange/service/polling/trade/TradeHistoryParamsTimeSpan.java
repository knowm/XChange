package com.xeiam.xchange.service.polling.trade;

import java.util.Date;

/**
 * @author Rafał Krupiński
 */
public interface TradeHistoryParamsTimeSpan extends TradeHistoryParamSinceTime {
  void setEndTime(Date time);

  Date getEndTime();
}

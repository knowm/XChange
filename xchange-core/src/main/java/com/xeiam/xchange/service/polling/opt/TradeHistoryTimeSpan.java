package com.xeiam.xchange.service.polling.opt;

import java.util.Date;

/**
 * @author Rafał Krupiński
 */
public interface TradeHistoryTimeSpan extends TradeHistorySinceTime {
  void setToTime(Date time);
}

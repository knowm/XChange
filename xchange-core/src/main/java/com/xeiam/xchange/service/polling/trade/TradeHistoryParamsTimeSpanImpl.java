package com.xeiam.xchange.service.polling.trade;

import java.util.Date;

/**
 * Common implementation of {@link TradeHistoryParamsTimeSpan}.
 */
public class TradeHistoryParamsTimeSpanImpl implements TradeHistoryParamsTimeSpan {
  private Date endTime;
  private Date startTime;

  public TradeHistoryParamsTimeSpanImpl() {
  }

  public TradeHistoryParamsTimeSpanImpl(Date startTime, Date endTime) {
    this.endTime = endTime;
    this.startTime = startTime;
  }

  public TradeHistoryParamsTimeSpanImpl(Date startTime) {
    this.startTime = startTime;
  }

  @Override
  public void setEndTime(Date time) {
    endTime = time;
  }

  @Override
  public Date getEndTime() {
    return endTime;
  }

  @Override
  public void setStartTime(Date time) {
    startTime = time;
  }

  @Override
  public Date getStartTime() {
    return startTime;
  }
}

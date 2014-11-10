package com.xeiam.xchange.service.polling.trade;

/**
 * Common implementation of {@link TradeHistoryParamsTimeSpan}.
 */
public class TradeHistoryParamsTimeSpanImpl implements TradeHistoryParamsTimeSpan {
  private Long endTime;
  private Long startTime;

  public TradeHistoryParamsTimeSpanImpl() {
  }

  public TradeHistoryParamsTimeSpanImpl(Long startTime, Long endTime) {
    this.endTime = endTime;
    this.startTime = startTime;
  }

  public TradeHistoryParamsTimeSpanImpl(Long startTime) {
    this.startTime = startTime;
  }

  @Override
  public void setEndTime(Long endTime) {
    this.endTime = endTime;
  }

  @Override
  public Long getEndTime() {
    return endTime;
  }

  @Override
  public void setStartTime(Long time) {
    startTime = time;
  }

  @Override
  public Long getStartTime() {
    return startTime;
  }
}

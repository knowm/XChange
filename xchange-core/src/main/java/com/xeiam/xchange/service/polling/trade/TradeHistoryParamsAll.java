package com.xeiam.xchange.service.polling.trade;

import java.util.Date;

/**
 * Generic {@link TradeHistoryParams} implementation that implements all the interfaces in the hierarchy and can be safely (without getting exceptions, if that all the required fields are non-null) passed to any implementation of {@link com.xeiam.xchange.service.polling.PollingTradeService#getTradeHistory(TradeHistoryParams)}.
 */
public class TradeHistoryParamsAll implements TradeHistoryParamsTimeSpan, TradeHistoryParamCount, TradeHistoryParamSinceIndex {
  private Long count;
  private Long startIndex;
  private Date endTime;
  private Date startTime;

  @Override
  public void setCount(Long count) {

    this.count = count;
  }

  @Override
  public Long getCount() {

    return count;
  }

  @Override
  public Long getStartIndex() {

    return startIndex;
  }

  @Override
  public void setStartIndex(Long from) {

    startIndex = from;
  }

  @Override
  public void setEndTime(Date to) {

    endTime = to;
  }

  @Override
  public Date getEndTime() {

    return endTime;
  }

  @Override
  public void setStartTime(Date from) {

    startTime = from;
  }

  @Override
  public Date getStartTime() {

    return startTime;
  }
}

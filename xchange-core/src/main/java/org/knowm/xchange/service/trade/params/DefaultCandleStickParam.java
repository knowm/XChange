package org.knowm.xchange.service.trade.params;

import java.util.Date;

public class DefaultCandleStickParam implements CandleStickDataParams {
  private final Date startDate;
  private final Date endDate;
  private final long periodInSecs;

  public DefaultCandleStickParam(Date startDate, Date endDate, long periodInSecs) {
    this.startDate = startDate;
    this.endDate = endDate;
    this.periodInSecs = periodInSecs;
  }

  public Date getStartDate() {
    return startDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public long getPeriodInSecs() {
    return periodInSecs;
  }
}

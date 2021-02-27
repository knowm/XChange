package org.knowm.xchange.binance.dto.meta.exchangeinfo;

public class RateLimit {
  private String limit;

  private String interval;

  private String intervalNum;

  private String rateLimitType;

  public String getLimit() {
    return limit;
  }

  public void setLimit(String limit) {
    this.limit = limit;
  }

  public String getInterval() {
    return interval;
  }

  public void setInterval(String interval) {
    this.interval = interval;
  }

  public String getIntervalNum() {
    return intervalNum;
  }

  public void setIntervalNum(String intervalNum) {
    this.intervalNum = intervalNum;
  }

  public String getRateLimitType() {
    return rateLimitType;
  }

  public void setRateLimitType(String rateLimitType) {
    this.rateLimitType = rateLimitType;
  }

  @Override
  public String toString() {
    return "ClassPojo [limit = "
        + limit
        + ", interval = "
        + interval
        + ", intervalNum = "
        + intervalNum
        + ", rateLimitType = "
        + rateLimitType
        + "]";
  }
}

package org.knowm.xchange.bittrex.service;

public enum BittrexChartDataPeriodType {
  ONE_MIN("oneMin"),
  FIVE_MIN("fiveMin"),
  THIRTY_MIN("thirtyMin"),
  ONE_HOUR("hour"),
  ONE_DAY("day");

  private final String period;

  BittrexChartDataPeriodType(String period) {
    this.period = period;
  }

  public String getPeriod() {
    return period;
  }
}

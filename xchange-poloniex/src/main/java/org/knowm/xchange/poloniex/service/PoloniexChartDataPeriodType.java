package org.knowm.xchange.poloniex.service;

public enum PoloniexChartDataPeriodType {
  PERIOD_300(300),
  PERIOD_900(900),
  PERIOD_1800(1800),
  PERIOD_7200(7200),
  PERIOD_14400(14400),
  PERIOD_86400(86400);

  private final int period;

  PoloniexChartDataPeriodType(int period) {
    this.period = period;
  }

  public int getPeriod() {
    return period;
  }
}

package org.knowm.xchange.poloniex.service;

public enum PoloniexChartDataPeriodType {
  PERIOD_300(300),
  PERIOD_900(900),
  PERIOD_1800(1800),
  PERIOD_7200(7200),
  PERIOD_14400(14400),
  PERIOD_86400(86400);

  private final long periodInSecs;

  PoloniexChartDataPeriodType(int periodInSecs) {
    this.periodInSecs = periodInSecs;
  }

  static PoloniexChartDataPeriodType getPeriodTypeFromSecs(long periodInSecs) {
    PoloniexChartDataPeriodType result = null;
    for (PoloniexChartDataPeriodType period : PoloniexChartDataPeriodType.values()) {
      if (period.periodInSecs == periodInSecs) {
        result = period;
        break;
      }
    }
    return result;
  }

  public static long[] getSupportedPeriodsInSecs() {
    long[] result = new long[PoloniexChartDataPeriodType.values().length];
    int index = 0;
    for (PoloniexChartDataPeriodType period : PoloniexChartDataPeriodType.values()) {
      result[index++] = period.getPeriodInSecs();
    }
    return result;
  }

  public long getPeriodInSecs() {
    return periodInSecs;
  }
}

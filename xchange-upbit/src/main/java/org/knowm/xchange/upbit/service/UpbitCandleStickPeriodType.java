package org.knowm.xchange.upbit.service;

public enum UpbitCandleStickPeriodType {
  WEEK("weeks", 60 * 60 * 24 * 7),
  DAY("days", 60 * 60 * 24),
  MINUTE("minutes", 60);

  private final String pathName;
  private final Integer periodInSeconds;

  UpbitCandleStickPeriodType(String pathName, Integer periodInSeconds) {
    this.pathName = pathName;
    this.periodInSeconds = periodInSeconds;
  }

  public String getPathName() {
    return pathName;
  }

  static UpbitCandleStickPeriodType getPeriodTypeFromSecs(long seconds) {
    UpbitCandleStickPeriodType result = null;
    for (UpbitCandleStickPeriodType period : UpbitCandleStickPeriodType.values()) {
      if (seconds % period.periodInSeconds == 0) {
        result = period;
        break;
      }
    }
    return result;
  }

  public static long[] getSupportedPeriodsInSecs() {
    long[] result = new long[UpbitCandleStickPeriodType.values().length];
    int index = 0;
    for (UpbitCandleStickPeriodType period : UpbitCandleStickPeriodType.values()) {
      result[index++] = period.periodInSeconds;
    }
    return result;
  }

  public long getUnitCount(long seconds) {
    return seconds / periodInSeconds;
  }
}

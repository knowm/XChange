package org.knowm.xchange.huobi.dto.marketdata;

import static java.util.concurrent.TimeUnit.DAYS;
import static java.util.concurrent.TimeUnit.HOURS;
import static java.util.concurrent.TimeUnit.MINUTES;

public enum KlineInterval {
  m1("1min", MINUTES.toMillis(1)),
  m5("5min", MINUTES.toMillis(5)),
  m15("15min", MINUTES.toMillis(15)),
  m30("30min", MINUTES.toMillis(30)),
  h1("60min", HOURS.toMillis(1)),
  h4("4hour", HOURS.toMillis(4)),

  d1("1day", DAYS.toMillis(1)),
  w1("1week", DAYS.toMillis(7)),
  M1("1mon", DAYS.toMillis(30)),
  Y1("1year", DAYS.toMillis(365));

  private final String code;
  private final Long millis;

  private KlineInterval(String code, Long millis) {
    this.millis = millis;
    this.code = code;
  }

  public Long getMillis() {
    return millis;
  }

  public String code() {
    return code;
  }
}

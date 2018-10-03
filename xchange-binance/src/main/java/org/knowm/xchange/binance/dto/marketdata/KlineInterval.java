package org.knowm.xchange.binance.dto.marketdata;

import static java.util.concurrent.TimeUnit.DAYS;
import static java.util.concurrent.TimeUnit.HOURS;
import static java.util.concurrent.TimeUnit.MINUTES;

public enum KlineInterval {
  m1("1m", MINUTES.toMillis(1)),
  m3("3m", MINUTES.toMillis(3)),
  m5("5m", MINUTES.toMillis(5)),
  m15("15m", MINUTES.toMillis(15)),
  m30("30m", MINUTES.toMillis(30)),

  h1("1h", HOURS.toMillis(1)),
  h2("2h", HOURS.toMillis(2)),
  h4("4h", HOURS.toMillis(4)),
  h6("6h", HOURS.toMillis(6)),
  h8("8h", HOURS.toMillis(8)),
  h12("12h", HOURS.toMillis(12)),

  d1("1d", DAYS.toMillis(1)),
  d3("3d", DAYS.toMillis(3)),

  w1("1w", DAYS.toMillis(7)),

  M1("1M", DAYS.toMillis(30));

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

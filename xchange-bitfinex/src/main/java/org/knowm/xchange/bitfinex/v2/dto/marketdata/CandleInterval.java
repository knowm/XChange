package org.knowm.xchange.bitfinex.v2.dto.marketdata;

import static java.util.concurrent.TimeUnit.DAYS;
import static java.util.concurrent.TimeUnit.HOURS;
import static java.util.concurrent.TimeUnit.MINUTES;

// Available values: '1m', '5m', '15m', '30m', '1h', '3h', '6h', '12h', '1D', '7D', '14D', '1M'
public enum CandleInterval {
  m1("1m", MINUTES.toMillis(1)),
  m5("5m", MINUTES.toMillis(5)),
  m15("15m", MINUTES.toMillis(15)),
  m30("30m", MINUTES.toMillis(30)),

  h1("1h", HOURS.toMillis(1)),
  h3("3h", HOURS.toMillis(3)),
  h6("6h", HOURS.toMillis(6)),
  h12("12h", HOURS.toMillis(12)),

  d1("1D", DAYS.toMillis(1)),
  d7("7D", DAYS.toMillis(7)),
  d14("14D", DAYS.toMillis(7)),

  M1("1M", DAYS.toMillis(30));

  private final String code;
  private final Long millis;

  private CandleInterval(String code, Long millis) {
    this.millis = millis;
    this.code = code;
  }

  public Long getMillis() {
    return millis;
  }

  public String getCode() {
    return code;
  }
}

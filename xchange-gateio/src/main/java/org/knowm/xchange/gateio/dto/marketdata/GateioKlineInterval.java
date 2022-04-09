package org.knowm.xchange.gateio.dto.marketdata;

import static java.util.concurrent.TimeUnit.DAYS;
import static java.util.concurrent.TimeUnit.HOURS;
import static java.util.concurrent.TimeUnit.MINUTES;
import static java.util.concurrent.TimeUnit.SECONDS;

public enum GateioKlineInterval {
  s10("10s", SECONDS.toSeconds(10)),
  m1("1m", MINUTES.toSeconds(1)),
  m5("5m", MINUTES.toSeconds(5)),
  m15("15m", MINUTES.toSeconds(15)),
  m30("30m", MINUTES.toSeconds(30)),
  h1("1h", HOURS.toSeconds(1)),
  h4("4h", HOURS.toSeconds(4)),
  h8("8h", HOURS.toSeconds(8)),

  d1("1d", DAYS.toSeconds(1)),
  w1("7d", DAYS.toSeconds(7));

  private final String code;
  private final Long seconds;

  private GateioKlineInterval(String code, Long seconds) {
    this.seconds = seconds;
    this.code = code;
  }

  public Long getSeconds() {
    return seconds;
  }

  public String code() {
    return code;
  }
}

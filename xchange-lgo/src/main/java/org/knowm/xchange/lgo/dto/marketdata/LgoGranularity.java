package org.knowm.xchange.lgo.dto.marketdata;

public enum LgoGranularity {
  ONE_MINUTE(60),
  FIVE_MINUTES(300),
  FIFTEEN_MINUTES(900),
  ONE_HOUR(3600),
  SIX_HOURS(21600),
  ONE_DAY(86400);

  private final int seconds;

  LgoGranularity(int seconds) {
    this.seconds = seconds;
  }

  public int asSeconds() {
    return seconds;
  }
}

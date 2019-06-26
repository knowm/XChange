package org.knowm.xchange.okcoin.dto.marketdata;

public enum OkCoinKlineType {
  MIN1("1min"),
  MIN3("3min"),
  MIN5("5min"),
  MIN15("15min"),
  MIN30("30min"),
  HOURE1("1hour"),
  HOURE2("2hour"),
  HOURE4("4hour"),
  HOURE6("6hour"),
  HOURE12("12hour"),
  DAY1("1day"),
  DAY3("3day"),
  WEEK1("1week");

  private final String type;

  private OkCoinKlineType(String type) {
    this.type = type;
  }

  public String getType() {
    return type;
  }
}

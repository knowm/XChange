package org.knowm.xchange.bitz.dto.marketdata;

public enum BitZKlineResolution {
  MIN1("1min"),
  MIN5("5min"),
  MIN15("15min"),
  MIN30("30min"),
  MIN60("60min"),
  HOUR4("4hour"),
  DAY1("1day"),
  DAY5("5day"),
  WEEK1("1week"),
  MON1("1mon");

  private final String code;

  private BitZKlineResolution(String code) {
    this.code = code;
  }

  public String code() {
    return code;
  }
}

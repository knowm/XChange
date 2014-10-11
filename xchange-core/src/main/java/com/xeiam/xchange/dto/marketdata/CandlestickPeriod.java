package com.xeiam.xchange.dto.marketdata;

public enum CandlestickPeriod {
  M1(60), M5(300), M15(900), M30(1800), H1(3600), H2(7200), H4(14400), H6(21600), H12(43200), D1(86400), D3(259200), W1(604800);

  private int seconds;

  private CandlestickPeriod(int seconds) {

    this.seconds = seconds;
  }

  public long getMillis() {

    return (long) seconds * 1000L;
  }

  public int getSeconds() {

    return seconds;
  }

  public int getMinutes() {

    return seconds / 60;
  }
}

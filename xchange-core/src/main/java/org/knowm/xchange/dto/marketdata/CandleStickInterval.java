package org.knowm.xchange.dto.marketdata;

public enum CandleStickInterval {
  s1(1),
  s3(3),
  s5(5),
  m1(60),
  m3(180),
  m5(300),
  m15(900),
  m30(1800),
  h1(3600),
  h2(7200),
  h3(10800),
  h4(14400),
  h6(21600),
  h12(43200),
  d1(86400),
  d2(172800),
  d3(259200),
  d5(432000),
  w1(604800),
  M1(2592000),
  M3(7776000);
  private final long seconds;

  private CandleStickInterval(long seconds) {
    this.seconds = seconds;
  }
}

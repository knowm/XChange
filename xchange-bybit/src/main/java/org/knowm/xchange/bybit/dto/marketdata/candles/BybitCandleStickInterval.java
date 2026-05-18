package org.knowm.xchange.bybit.dto.marketdata.candles;

import lombok.Getter;

@Getter
public enum BybitCandleStickInterval {
  m1("1"),
  m3("3"),
  m5("5"),
  m15("15"),
  m30("30"),
  m60("60"),
  m120("120"),
  m240("240"),
  m360("360"),
  m720("720"),
  d1("D"),
  w1("W"),
  M1("M");
  private final String code;

  private BybitCandleStickInterval(String code) {
    this.code = code;
  }
}

package org.knowm.xchange.ascendex.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;

public class AscendexBarHistDto implements Serializable {

  private final String m;
  private final String symbol;
  private final AscendexBarDto bar;

  public AscendexBarHistDto(
      @JsonProperty("m") String m,
      @JsonProperty("s") String symbol,
      @JsonProperty("data") AscendexBarDto bar) {
    this.m = m;
    this.symbol = symbol;
    this.bar = bar;
  }

  public String getM() {
    return m;
  }

  public String getSymbol() {
    return symbol;
  }

  public AscendexBarDto getBar() {
    return bar;
  }

  @Override
  public String toString() {
    return "AscendexBarHistDto{"
        + "m='"
        + m
        + '\''
        + ", symbol='"
        + symbol
        + '\''
        + ", bar="
        + bar
        + '}';
  }
}

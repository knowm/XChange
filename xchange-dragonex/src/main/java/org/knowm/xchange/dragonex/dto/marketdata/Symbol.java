package org.knowm.xchange.dragonex.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Symbol {

  public final String symbol;
  public final long symbolId;

  public Symbol(@JsonProperty("symbol") String symbol, @JsonProperty("symbol_id") long symbolId) {
    this.symbol = symbol;
    this.symbolId = symbolId;
  }

  @Override
  public String toString() {
    return "Coin ["
        + (symbol != null ? "symbol=" + symbol + ", " : "")
        + "symbolId="
        + symbolId
        + "]";
  }
}

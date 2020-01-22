package org.knowm.xchange.cryptowatch.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CryptowatchAsset {

  private final String symbol;
  private final String name;
  private final boolean fiat;

  public CryptowatchAsset(
      @JsonProperty("symbol") String symbol,
      @JsonProperty("name") String name,
      @JsonProperty("fiat") boolean fiat) {
    this.symbol = symbol;
    this.name = name;
    this.fiat = fiat;
  }

  public String getSymbol() {
    return symbol;
  }

  public String getName() {
    return name;
  }

  public boolean isFiat() {
    return fiat;
  }

  @Override
  public String toString() {
    return "CryptowatchAsset{"
        + "symbol='"
        + symbol
        + '\''
        + ", name='"
        + name
        + '\''
        + ", fiat="
        + fiat
        + '}';
  }
}

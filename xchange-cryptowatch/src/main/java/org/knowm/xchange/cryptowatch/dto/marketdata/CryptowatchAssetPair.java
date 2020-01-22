package org.knowm.xchange.cryptowatch.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CryptowatchAssetPair {

  private final String symbol;
  private final CryptowatchAsset base;
  private final CryptowatchAsset quote;

  public CryptowatchAssetPair(
      @JsonProperty("symbol") String symbol,
      @JsonProperty("base") CryptowatchAsset base,
      @JsonProperty("quote") CryptowatchAsset quote) {
    this.symbol = symbol;
    this.base = base;
    this.quote = quote;
  }

  public String getSymbol() {
    return symbol;
  }

  public CryptowatchAsset getBase() {
    return base;
  }

  public CryptowatchAsset getQuote() {
    return quote;
  }

  @Override
  public String toString() {
    return "CryptowatchAssetPair{" + "symbol='" + symbol + '\'' + '}';
  }
}

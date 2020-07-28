package org.knowm.xchange.cryptowatch.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@ToString
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
}

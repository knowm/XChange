package org.knowm.xchange.cryptowatch.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@ToString
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
}

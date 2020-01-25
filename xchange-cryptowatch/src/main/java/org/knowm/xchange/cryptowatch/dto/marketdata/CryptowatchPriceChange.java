package org.knowm.xchange.cryptowatch.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@ToString
public class CryptowatchPriceChange {

  private final BigDecimal percentage;
  private final BigDecimal absolute;

  public CryptowatchPriceChange(
      @JsonProperty("percentage") BigDecimal percentage,
      @JsonProperty("absolute") BigDecimal absolute) {
    this.percentage = percentage;
    this.absolute = absolute;
  }
}

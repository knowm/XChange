package org.knowm.xchange.coinbasepro.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class CoinbaseProCurrency {

  private final BigDecimal maxPrecision;
  private final String name;
  private final String minSize;
  private final CoinbaseProCurrencyDetails details;
  private final String id;
  private final Object message;
  private final String status;

  public CoinbaseProCurrency(
      @JsonProperty("max_precision") BigDecimal maxPrecision,
      @JsonProperty("name") String name,
      @JsonProperty("min_size") String minSize,
      @JsonProperty("details") CoinbaseProCurrencyDetails details,
      @JsonProperty("id") String id,
      @JsonProperty("message") Object message,
      @JsonProperty("status") String status) {
    this.maxPrecision = maxPrecision;
    this.name = name;
    this.minSize = minSize;
    this.details = details;
    this.id = id;
    this.message = message;
    this.status = status;
  }
}

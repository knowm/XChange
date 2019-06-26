package org.knowm.xchange.coinbasepro.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class CoinbaseProProduct {

  private final String id;
  private final String baseCurrency;
  private final String targetCurrency;
  private final BigDecimal baseMinSize;
  private final BigDecimal baseMaxSize;
  private final BigDecimal baseIncrement;
  private final BigDecimal quoteIncrement;

  public CoinbaseProProduct(
      @JsonProperty("id") String id,
      @JsonProperty("base_currency") String baseCurrency,
      @JsonProperty("quote_currency") String targetCurrency,
      @JsonProperty("base_min_size") BigDecimal baseMinSize,
      @JsonProperty("base_max_size") BigDecimal baseMaxSize,
      @JsonProperty("base_increment") BigDecimal baseIncrement,
      @JsonProperty("quote_increment") BigDecimal quoteIncrement) {

    this.id = id;
    this.baseCurrency = baseCurrency;
    this.targetCurrency = targetCurrency;
    this.baseMinSize = baseMinSize;
    this.baseMaxSize = baseMaxSize;
    this.baseIncrement = baseIncrement;
    this.quoteIncrement = quoteIncrement;
  }

  public String getId() {

    return id;
  }

  public String getBaseCurrency() {

    return baseCurrency;
  }

  public String getTargetCurrency() {

    return targetCurrency;
  }

  public BigDecimal getBaseMinSize() {

    return baseMinSize;
  }

  public BigDecimal getBaseMaxSize() {

    return baseMaxSize;
  }

  public BigDecimal getBaseIncrement() {
    return baseIncrement;
  }

  public BigDecimal getQuoteIncrement() {

    return quoteIncrement;
  }
}

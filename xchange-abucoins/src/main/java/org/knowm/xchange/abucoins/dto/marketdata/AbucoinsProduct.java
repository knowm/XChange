package org.knowm.xchange.abucoins.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AbucoinsProduct {
  /**identifier of market*/
  String id;
  /** which currency is buying/selling */
  String baseCurrency;
  /** price currency of base_currency */
  String quoteCurrency;
  /** minimum order size */
  BigDecimal baseMinSize;
  /** maximum order size */
  BigDecimal baseMaxSize;
  /** the order price must be a multiple of this increment */
  BigDecimal quoteIncrement;
  String displayName;

  public AbucoinsProduct(@JsonProperty("id") String id,
                         @JsonProperty("base_currency") String baseCurrency,
                         @JsonProperty("quote_currency") String quoteCurrency,
                         @JsonProperty("base_min_size") BigDecimal baseMinSize,
                         @JsonProperty("base_max_size") BigDecimal baseMaxSize,
                         @JsonProperty("quote_increment") BigDecimal quoteIncrement,
                         @JsonProperty("base_min_size") String displayName) {
    this.id = id;
    this.baseCurrency = baseCurrency;
    this.quoteCurrency = quoteCurrency;
    this.baseMinSize = baseMinSize;
    this.baseMaxSize = baseMaxSize;
    this.quoteIncrement = quoteIncrement;
    this.displayName = displayName;
  }

  public String getId() {
    return id;
  }

  public String getBaseCurrency() {
    return baseCurrency;
  }

  public String getQuoteCurrency() {
    return quoteCurrency;
  }

  public BigDecimal getBaseMinSize() {
    return baseMinSize;
  }

  public BigDecimal getBaseMaxSize() {
    return baseMaxSize;
  }

  public BigDecimal getQuoteIncrement() {
    return quoteIncrement;
  }

  public String getDisplayName() {
    return displayName;
  }

  @Override
  public String toString() {
    return "AbucoinsProduct [id=" + id + ", baseCurrency=" + baseCurrency + ", quoteCurrency=" + quoteCurrency
        + ", baseMinSize=" + baseMinSize + ", baseMaxSize=" + baseMaxSize + ", quoteIncrement=" + quoteIncrement
        + ", displayName=" + displayName + "]";
  }
}

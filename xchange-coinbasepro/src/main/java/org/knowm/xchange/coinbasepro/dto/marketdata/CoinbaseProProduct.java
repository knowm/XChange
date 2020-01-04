package org.knowm.xchange.coinbasepro.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class CoinbaseProProduct {

  private final String id;
  private final String baseCurrency;
  private final String targetCurrency;
  private final BigDecimal baseMinSize;
  private final BigDecimal baseMaxSize;
  private final BigDecimal minMarketFunds;
  private final BigDecimal maxMarketFunds;
  private final BigDecimal baseIncrement;
  private final BigDecimal quoteIncrement;
  private final boolean limitOnly;
  private final String status;

  public CoinbaseProProduct(
      @JsonProperty("id") String id,
      @JsonProperty("base_currency") String baseCurrency,
      @JsonProperty("quote_currency") String targetCurrency,
      @JsonProperty("base_min_size") BigDecimal baseMinSize,
      @JsonProperty("base_max_size") BigDecimal baseMaxSize,
      @JsonProperty("min_market_funds") BigDecimal minMarketFunds,
      @JsonProperty("max_market_funds") BigDecimal maxMarketFunds,
      @JsonProperty("base_increment") BigDecimal baseIncrement,
      @JsonProperty("quote_increment") BigDecimal quoteIncrement,
      @JsonProperty("limit_only") boolean limitOnly,
      @JsonProperty("status") String status) {

    this.id = id;
    this.baseCurrency = baseCurrency;
    this.targetCurrency = targetCurrency;
    this.baseMinSize = baseMinSize;
    this.baseMaxSize = baseMaxSize;
    this.minMarketFunds = minMarketFunds;
    this.maxMarketFunds = maxMarketFunds;
    this.baseIncrement = baseIncrement;
    this.quoteIncrement = quoteIncrement;
    this.limitOnly = limitOnly;
    this.status = status;
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

  public BigDecimal getMinMarketFunds() {
    return minMarketFunds;
  }

  public BigDecimal getMaxMarketFunds() {
    return maxMarketFunds;
  }

  public boolean isLimitOnly() {
    return limitOnly;
  }

  public String getStatus() {
    return status;
  }
}

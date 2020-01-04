package org.knowm.xchange.cobinhood.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class CobinhoodCurrencyPair {

  private final String baseCurrencyId;
  private final boolean marginEnabled;
  private final BigDecimal makerFee;
  private final BigDecimal quoteIncrement;
  private final BigDecimal baseMaxSize;
  private final BigDecimal takerFee;
  private final String quoteCurrencyId;
  private final String id;
  private final BigDecimal baseMinSize;

  public CobinhoodCurrencyPair(
      @JsonProperty("base_currency_id") String baseCurrencyId,
      @JsonProperty("margin_enabled") boolean marginEnabled,
      @JsonProperty("maker_fee") BigDecimal makerFee,
      @JsonProperty("quote_increment") BigDecimal quoteIncrement,
      @JsonProperty("base_max_size") BigDecimal baseMaxSize,
      @JsonProperty("taker_fee") BigDecimal takerFee,
      @JsonProperty("quote_currency_id") String quoteCurrencyId,
      @JsonProperty("id") String id,
      @JsonProperty("base_min_size") BigDecimal baseMinSize) {
    this.baseCurrencyId = baseCurrencyId;
    this.marginEnabled = marginEnabled;
    this.makerFee = makerFee;
    this.quoteIncrement = quoteIncrement;
    this.baseMaxSize = baseMaxSize;
    this.takerFee = takerFee;
    this.quoteCurrencyId = quoteCurrencyId;
    this.id = id;
    this.baseMinSize = baseMinSize;
  }

  public String getBaseCurrencyId() {
    return baseCurrencyId;
  }

  public boolean isMarginEnabled() {
    return marginEnabled;
  }

  public BigDecimal getMakerFee() {
    return makerFee;
  }

  public BigDecimal getQuoteIncrement() {
    return quoteIncrement;
  }

  public BigDecimal getBaseMaxSize() {
    return baseMaxSize;
  }

  public BigDecimal getTakerFee() {
    return takerFee;
  }

  public String getQuoteCurrencyId() {
    return quoteCurrencyId;
  }

  public String getId() {
    return id;
  }

  public BigDecimal getBaseMinSize() {
    return baseMinSize;
  }

  @Override
  public String toString() {
    return "CobinhoodCurrencyPair{"
        + "baseCurrencyId='"
        + baseCurrencyId
        + '\''
        + ", marginEnabled="
        + marginEnabled
        + ", makerFee="
        + makerFee
        + ", quoteIncrement="
        + quoteIncrement
        + ", baseMaxSize="
        + baseMaxSize
        + ", takerFee="
        + takerFee
        + ", quoteCurrencyId='"
        + quoteCurrencyId
        + '\''
        + ", id='"
        + id
        + '\''
        + ", baseMinSize="
        + baseMinSize
        + '}';
  }
}

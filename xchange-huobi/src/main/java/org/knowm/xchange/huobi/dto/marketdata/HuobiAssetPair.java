package org.knowm.xchange.huobi.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class HuobiAssetPair {

  private final String baseCurrency;
  private final String quoteCurrency;
  private final Integer pricePrecision;
  private final Integer amountPrecision;
  private final String symbolPartition;
  private final BigDecimal minOrderAmount;
  private final BigDecimal minOrderValue;

  public HuobiAssetPair(
      @JsonProperty("base-currency") String baseCurrency,
      @JsonProperty("quote-currency") String quoteCurrency,
      @JsonProperty("price-precision") Integer pricePrecision,
      @JsonProperty("amount-precision") Integer amountPrecision,
      @JsonProperty("symbol-partition") String symbolPartition,
      @JsonProperty("min-order-amt") BigDecimal minOrderAmount,
      @JsonProperty("min-order-value") BigDecimal minOrderValue) {
    this.baseCurrency = baseCurrency;
    this.quoteCurrency = quoteCurrency;
    this.pricePrecision = pricePrecision;
    this.amountPrecision = amountPrecision;
    this.symbolPartition = symbolPartition;
    this.minOrderAmount = minOrderAmount;
    this.minOrderValue = minOrderValue;
  }

  public String getBaseCurrency() {
    return baseCurrency;
  }

  public String getQuoteCurrency() {
    return quoteCurrency;
  }

  public String getKey() {
    return baseCurrency + quoteCurrency;
  }

  public Integer getPricePrecision() {
    return pricePrecision;
  }

  public Integer getAmountPrecision() {
    return amountPrecision;
  }

  private String getSymbolPartition() {
    return symbolPartition;
  }

  public BigDecimal getMinOrderAmount() {
    return minOrderAmount;
  }

  public BigDecimal getMinOrderValue() {
    return minOrderValue;
  }

  @Override
  public String toString() {
    return String.format(
        "HuobiAssetPair [baseCurrency=%s, quoteCurrency=%s, pricePrecision=%s, "
            + "amountPrecision=%s, symbolPartition=%s, minOrderAmount=%s, minOrderValue=%s]",
        getBaseCurrency(),
        getQuoteCurrency(),
        getPricePrecision(),
        getAmountPrecision(),
        getSymbolPartition(),
        getMinOrderAmount(),
        getMinOrderValue());
  }

}

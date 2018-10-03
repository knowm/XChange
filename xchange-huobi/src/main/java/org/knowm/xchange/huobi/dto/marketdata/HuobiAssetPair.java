package org.knowm.xchange.huobi.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HuobiAssetPair {

  private final String baseCurrency;
  private final String quoteCurrency;
  private final String pricePrecision;
  private final String amountPrecision;
  private final String symbolPartition;

  public HuobiAssetPair(
      @JsonProperty("base-currency") String baseCurrency,
      @JsonProperty("quote-currency") String quoteCurrency,
      @JsonProperty("price-precision") String pricePrecision,
      @JsonProperty("amount-precision") String amountPrecision,
      @JsonProperty("symbol-partition") String symbolPartition) {
    this.baseCurrency = baseCurrency;
    this.quoteCurrency = quoteCurrency;
    this.pricePrecision = pricePrecision;
    this.amountPrecision = amountPrecision;
    this.symbolPartition = symbolPartition;
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

  public String getPricePrecision() {
    return pricePrecision;
  }

  private String getAmountPrecision() {
    return amountPrecision;
  }

  private String getSymbolPartition() {
    return symbolPartition;
  }

  @Override
  public String toString() {
    return String.format(
        "HuobiAssetPair [baseCurrency=%s, quoteCurrency=%s, pricePrecision=%s, "
            + "amountPrecision=%s, symbolPartition=%s]",
        getBaseCurrency(),
        getQuoteCurrency(),
        getPricePrecision(),
        getAmountPrecision(),
        getSymbolPartition());
  }
}

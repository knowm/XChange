package org.knowm.xchange.cryptopia.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class CryptopiaTradePair {

  private final long id;
  private final String label;
  private final String currency;
  private final String symbol;
  private final String baseCurrency;
  private final String baseSymbol;
  private final String status;
  private final String statusMessage;
  private final BigDecimal tradeFee;
  private final BigDecimal minimumTrade;
  private final BigDecimal maximumTrade;
  private final BigDecimal minimumBaseTrade;
  private final BigDecimal maximumBaseTrade;
  private final BigDecimal minimumPrice;
  private final BigDecimal maximumPrice;

  public CryptopiaTradePair(@JsonProperty("Id") long id, @JsonProperty("Label") String label, @JsonProperty("Currency") String currency, @JsonProperty("Symbol") String symbol,
      @JsonProperty("BaseCurrency") String baseCurrency, @JsonProperty("BaseSymbol") String baseSymbol, @JsonProperty("Status") String status,
      @JsonProperty("StatusMessage") String statusMessage, @JsonProperty("TradeFee") BigDecimal tradeFee, @JsonProperty("MinimumTrade") BigDecimal minimumTrade,
      @JsonProperty("MaximumTrade") BigDecimal maximumTrade, @JsonProperty("MinimumBaseTrade") BigDecimal minimumBaseTrade,
      @JsonProperty("MaximumBaseTrade") BigDecimal maximumBaseTrade, @JsonProperty("MinimumPrice") BigDecimal minimumPrice,
      @JsonProperty("MaximumPrice") BigDecimal maximumPrice) {
    this.id = id;
    this.label = label;
    this.currency = currency;
    this.symbol = symbol;
    this.baseCurrency = baseCurrency;
    this.baseSymbol = baseSymbol;
    this.status = status;
    this.statusMessage = statusMessage;
    this.tradeFee = tradeFee;
    this.minimumTrade = minimumTrade;
    this.maximumTrade = maximumTrade;
    this.minimumBaseTrade = minimumBaseTrade;
    this.maximumBaseTrade = maximumBaseTrade;
    this.minimumPrice = minimumPrice;
    this.maximumPrice = maximumPrice;
  }

  public long getId() {
    return id;
  }

  public String getLabel() {
    return label;
  }

  public String getCurrency() {
    return currency;
  }

  public String getSymbol() {
    return symbol;
  }

  public String getBaseCurrency() {
    return baseCurrency;
  }

  public String getBaseSymbol() {
    return baseSymbol;
  }

  public String getStatus() {
    return status;
  }

  public String getStatusMessage() {
    return statusMessage;
  }

  public BigDecimal getTradeFee() {
    return tradeFee;
  }

  public BigDecimal getMinimumTrade() {
    return minimumTrade;
  }

  public BigDecimal getMaximumTrade() {
    return maximumTrade;
  }

  public BigDecimal getMinimumBaseTrade() {
    return minimumBaseTrade;
  }

  public BigDecimal getMaximumBaseTrade() {
    return maximumBaseTrade;
  }

  public BigDecimal getMinimumPrice() {
    return minimumPrice;
  }

  public BigDecimal getMaximumPrice() {
    return maximumPrice;
  }

  @Override
  public String toString() {
    return "CryptopiaTradePair{" +
        "id=" + id +
        ", label='" + label + '\'' +
        ", currency='" + currency + '\'' +
        ", symbol='" + symbol + '\'' +
        ", baseCurrency='" + baseCurrency + '\'' +
        ", baseSymbol='" + baseSymbol + '\'' +
        ", status='" + status + '\'' +
        ", statusMessage='" + statusMessage + '\'' +
        ", tradeFee=" + tradeFee +
        ", minimumTrade=" + minimumTrade +
        ", maximumTrade=" + maximumTrade +
        ", minimumBaseTrade=" + minimumBaseTrade +
        ", maximumBaseTrade=" + maximumBaseTrade +
        ", minimumPrice=" + minimumPrice +
        ", maximumPrice=" + maximumPrice +
        '}';
  }
}

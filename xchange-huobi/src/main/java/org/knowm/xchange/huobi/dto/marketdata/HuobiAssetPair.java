package org.knowm.xchange.huobi.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class HuobiAssetPair {

  private final String baseCurrency;
  private final String quoteCurrency;
  private final Integer pricePrecision;
  private final Integer amountPrecision;
  private final String symbolPartition;
  private final String symbol;
  private final String state;
  private final Integer valuePrecision;
  private final BigDecimal minOrderAmount;
  private final BigDecimal maxOrderAmount;
  private final BigDecimal minOrderValue;
  private final BigDecimal limitOrderMinOrderAmt;
  private final BigDecimal limitOrderMaxOrderAmt;
  private final BigDecimal sellMarketMinOrderAmt;
  private final BigDecimal sellMarketMaxOrderAmt;
  private final BigDecimal buyMarketMaxOrderValue;
  private final String apiTrading;

  public HuobiAssetPair(
      @JsonProperty("base-currency") String baseCurrency,
      @JsonProperty("quote-currency") String quoteCurrency,
      @JsonProperty("price-precision") Integer pricePrecision,
      @JsonProperty("amount-precision") Integer amountPrecision,
      @JsonProperty("symbol-partition") String symbolPartition,
      @JsonProperty("symbol") String symbol,
      @JsonProperty("state") String state,
      @JsonProperty("value-precision") Integer valuePrecision,
      @JsonProperty("min-order-amt") BigDecimal minOrderAmount,
      @JsonProperty("max-order-amt") BigDecimal maxOrderAmount,
      @JsonProperty("min-order-value") BigDecimal minOrderValue,
      @JsonProperty("limit-order-min-order-amt") BigDecimal limitOrderMinOrderAmt,
      @JsonProperty("limit-order-max-order-amt") BigDecimal limitOrderMaxOrderAmt,
      @JsonProperty("sell-market-min-order-amt") BigDecimal sellMarketMinOrderAmt,
      @JsonProperty("sell-market-max-order-amt") BigDecimal sellMarketMaxOrderAmt,
      @JsonProperty("buy-market-max-order-value") BigDecimal buyMarketMaxOrderValue,
      @JsonProperty("api-trading") String apiTrading) {
    this.baseCurrency = baseCurrency;
    this.quoteCurrency = quoteCurrency;
    this.pricePrecision = pricePrecision;
    this.amountPrecision = amountPrecision;
    this.symbolPartition = symbolPartition;
    this.symbol = symbol;
    this.state = state;
    this.valuePrecision = valuePrecision;
    this.minOrderAmount = minOrderAmount;
    this.maxOrderAmount = maxOrderAmount;
    this.minOrderValue = minOrderValue;
    this.limitOrderMinOrderAmt = limitOrderMinOrderAmt;
    this.limitOrderMaxOrderAmt = limitOrderMaxOrderAmt;
    this.sellMarketMinOrderAmt = sellMarketMinOrderAmt;
    this.sellMarketMaxOrderAmt = sellMarketMaxOrderAmt;
    this.buyMarketMaxOrderValue = buyMarketMaxOrderValue;
    this.apiTrading = apiTrading;
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

  public String getSymbol() {
    return symbol;
  }

  public String getState() {
    return state;
  }

  public Integer getValuePrecision() {
    return valuePrecision;
  }

  public BigDecimal getMinOrderAmount() {
    return minOrderAmount;
  }

  public BigDecimal getMaxOrderAmount() {
    return maxOrderAmount;
  }

  public BigDecimal getMinOrderValue() {
    return minOrderValue;
  }

  public BigDecimal getLimitOrderMinOrderAmt() {
    return limitOrderMinOrderAmt;
  }

  public BigDecimal getLimitOrderMaxOrderAmt() {
    return limitOrderMaxOrderAmt;
  }

  public BigDecimal getSellMarketMinOrderAmt() {
    return sellMarketMinOrderAmt;
  }

  public BigDecimal getSellMarketMaxOrderAmt() {
    return sellMarketMaxOrderAmt;
  }

  public BigDecimal getBuyMarketMaxOrderValue() {
    return buyMarketMaxOrderValue;
  }

  public String getApiTrading() {
    return apiTrading;
  }

  @Override
  public String toString() {
    return "HuobiAssetPair ["
        + "baseCurrency='"
        + baseCurrency
        + '\''
        + ", quoteCurrency='"
        + quoteCurrency
        + '\''
        + ", pricePrecision="
        + pricePrecision
        + ", amountPrecision="
        + amountPrecision
        + ", symbolPartition='"
        + symbolPartition
        + '\''
        + ", symbol='"
        + symbol
        + '\''
        + ", state='"
        + state
        + '\''
        + ", valuePrecision="
        + valuePrecision
        + ", minOrderAmount="
        + minOrderAmount
        + ", maxOrderAmount="
        + maxOrderAmount
        + ", minOrderValue="
        + minOrderValue
        + ", limitOrderMinOrderAmt="
        + limitOrderMinOrderAmt
        + ", limitOrderMaxOrderAmt="
        + limitOrderMaxOrderAmt
        + ", sellMarketMinOrderAmt="
        + sellMarketMinOrderAmt
        + ", sellMarketMaxOrderAmt="
        + sellMarketMaxOrderAmt
        + ", buyMarketMaxOrderValue="
        + buyMarketMaxOrderValue
        + ", apiTrading='"
        + apiTrading
        + '\''
        + ']';
  }
}

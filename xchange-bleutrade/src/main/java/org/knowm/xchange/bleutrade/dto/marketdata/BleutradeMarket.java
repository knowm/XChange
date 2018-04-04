package org.knowm.xchange.bleutrade.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
  "MarketCurrency",
  "BaseCurrency",
  "MarketCurrencyLong",
  "BaseCurrencyLong",
  "MinTradeSize",
  "MarketName",
  "IsActive"
})
public class BleutradeMarket {

  @JsonProperty("MarketCurrency")
  private String MarketCurrency;

  @JsonProperty("BaseCurrency")
  private String BaseCurrency;

  @JsonProperty("MarketCurrencyLong")
  private String MarketCurrencyLong;

  @JsonProperty("BaseCurrencyLong")
  private String BaseCurrencyLong;

  @JsonProperty("MinTradeSize")
  private BigDecimal MinTradeSize;

  @JsonProperty("MarketName")
  private String MarketName;

  @JsonProperty("IsActive")
  private Boolean IsActive;

  @JsonIgnore private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  /** @return The MarketCurrency */
  @JsonProperty("MarketCurrency")
  public String getMarketCurrency() {

    return MarketCurrency;
  }

  /** @param MarketCurrency The MarketCurrency */
  @JsonProperty("MarketCurrency")
  public void setMarketCurrency(String MarketCurrency) {

    this.MarketCurrency = MarketCurrency;
  }

  /** @return The BaseCurrency */
  @JsonProperty("BaseCurrency")
  public String getBaseCurrency() {

    return BaseCurrency;
  }

  /** @param BaseCurrency The BaseCurrency */
  @JsonProperty("BaseCurrency")
  public void setBaseCurrency(String BaseCurrency) {

    this.BaseCurrency = BaseCurrency;
  }

  /** @return The MarketCurrencyLong */
  @JsonProperty("MarketCurrencyLong")
  public String getMarketCurrencyLong() {

    return MarketCurrencyLong;
  }

  /** @param MarketCurrencyLong The MarketCurrencyLong */
  @JsonProperty("MarketCurrencyLong")
  public void setMarketCurrencyLong(String MarketCurrencyLong) {

    this.MarketCurrencyLong = MarketCurrencyLong;
  }

  /** @return The BaseCurrencyLong */
  @JsonProperty("BaseCurrencyLong")
  public String getBaseCurrencyLong() {

    return BaseCurrencyLong;
  }

  /** @param BaseCurrencyLong The BaseCurrencyLong */
  @JsonProperty("BaseCurrencyLong")
  public void setBaseCurrencyLong(String BaseCurrencyLong) {

    this.BaseCurrencyLong = BaseCurrencyLong;
  }

  /** @return The MinTradeSize */
  @JsonProperty("MinTradeSize")
  public BigDecimal getMinTradeSize() {

    return MinTradeSize;
  }

  /** @param MinTradeSize The MinTradeSize */
  @JsonProperty("MinTradeSize")
  public void setMinTradeSize(BigDecimal MinTradeSize) {

    this.MinTradeSize = MinTradeSize;
  }

  /** @return The MarketName */
  @JsonProperty("MarketName")
  public String getMarketName() {

    return MarketName;
  }

  /** @param MarketName The MarketName */
  @JsonProperty("MarketName")
  public void setMarketName(String MarketName) {

    this.MarketName = MarketName;
  }

  /** @return The IsActive */
  @JsonProperty("IsActive")
  public Boolean getIsActive() {

    return IsActive;
  }

  /** @param IsActive The IsActive */
  @JsonProperty("IsActive")
  public void setIsActive(Boolean IsActive) {

    this.IsActive = IsActive;
  }

  @JsonAnyGetter
  public Map<String, Object> getAdditionalProperties() {

    return this.additionalProperties;
  }

  @JsonAnySetter
  public void setAdditionalProperty(String name, Object value) {

    this.additionalProperties.put(name, value);
  }

  @Override
  public String toString() {

    return "BleutradeMarket [MarketCurrency="
        + MarketCurrency
        + ", BaseCurrency="
        + BaseCurrency
        + ", MarketCurrencyLong="
        + MarketCurrencyLong
        + ", BaseCurrencyLong="
        + BaseCurrencyLong
        + ", MinTradeSize="
        + MinTradeSize
        + ", MarketName="
        + MarketName
        + ", IsActive="
        + IsActive
        + ", additionalProperties="
        + additionalProperties
        + "]";
  }
}

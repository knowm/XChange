package org.knowm.xchange.bittrex.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BittrexSymbol {

  private String baseCurrency;
  private String baseCurrencyLong;
  private String created;
  private boolean isActive;
  private String marketCurrency;
  private String marketCurrencyLong;
  private String marketName;
  private Number minTradeSize;

  public BittrexSymbol(
      @JsonProperty("BaseCurrency") String baseCurrency,
      @JsonProperty("BaseCurrencyLong") String baseCurrencyLong,
      @JsonProperty("Created") String created,
      @JsonProperty("IsActive") boolean isActive,
      @JsonProperty("MarketCurrency") String marketCurrency,
      @JsonProperty("MarketCurrencyLong") String marketCurrencyLong,
      @JsonProperty("MarketName") String marketName,
      @JsonProperty("MinTradeSize") Number minTradeSize) {

    this.baseCurrency = baseCurrency;
    this.baseCurrencyLong = baseCurrencyLong;
    this.created = created;
    this.isActive = isActive;
    this.marketCurrency = marketCurrency;
    this.marketCurrencyLong = marketCurrencyLong;
    this.marketName = marketName;
    this.minTradeSize = minTradeSize;
  }

  public String getBaseCurrency() {

    return this.baseCurrency;
  }

  public void setBaseCurrency(String baseCurrency) {

    this.baseCurrency = baseCurrency;
  }

  public String getBaseCurrencyLong() {

    return this.baseCurrencyLong;
  }

  public void setBaseCurrencyLong(String baseCurrencyLong) {

    this.baseCurrencyLong = baseCurrencyLong;
  }

  public String getCreated() {

    return this.created;
  }

  public void setCreated(String created) {

    this.created = created;
  }

  public boolean getIsActive() {

    return this.isActive;
  }

  public void setIsActive(boolean isActive) {

    this.isActive = isActive;
  }

  public String getMarketCurrency() {

    return this.marketCurrency;
  }

  public void setMarketCurrency(String marketCurrency) {

    this.marketCurrency = marketCurrency;
  }

  public String getMarketCurrencyLong() {

    return this.marketCurrencyLong;
  }

  public void setMarketCurrencyLong(String marketCurrencyLong) {

    this.marketCurrencyLong = marketCurrencyLong;
  }

  public String getMarketName() {

    return this.marketName;
  }

  public void setMarketName(String marketName) {

    this.marketName = marketName;
  }

  public Number getMinTradeSize() {

    return this.minTradeSize;
  }

  public void setMinTradeSize(Number minTradeSize) {

    this.minTradeSize = minTradeSize;
  }

  @Override
  public String toString() {

    return "BittrexSymbol [baseCurrency="
        + baseCurrency
        + ", baseCurrencyLong="
        + baseCurrencyLong
        + ", created="
        + created
        + ", isActive="
        + isActive
        + ", marketCurrency="
        + marketCurrency
        + ", marketCurrencyLong="
        + marketCurrencyLong
        + ", marketName="
        + marketName
        + ", minTradeSize="
        + minTradeSize
        + "]";
  }
}

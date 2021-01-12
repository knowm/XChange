package org.knowm.xchange.ftx.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class FtxMarketDto {

  @JsonProperty("ask")
  private final BigDecimal ask;

  @JsonProperty("baseCurrency")
  private final String baseCurrency;

  @JsonProperty("bid")
  private final BigDecimal bid;

  @JsonProperty("change1h")
  private final BigDecimal change1h;

  @JsonProperty("change24h")
  private final BigDecimal change24h;

  @JsonProperty("changeBod")
  private final BigDecimal changeBod;

  @JsonProperty("enabled")
  private final boolean enabled;

  @JsonProperty("highLeverageFeeExempt")
  private final boolean highLeverageFeeExempt;

  @JsonProperty("last")
  private final BigDecimal last;

  @JsonProperty("minProvideSize")
  private final BigDecimal minProvideSize;

  @JsonProperty("name")
  private final String name;

  @JsonProperty("postOnly")
  private final boolean postOnly;

  @JsonProperty("price")
  private final BigDecimal price;

  @JsonProperty("priceIncrement")
  private final BigDecimal priceIncrement;

  @JsonProperty("quoteCurrency")
  private final String quoteCurrency;

  @JsonProperty("quoteVolume24h")
  private final BigDecimal quoteVolume24h;

  @JsonProperty("restricted")
  private final boolean restricted;

  @JsonProperty("sizeIncrement")
  private final BigDecimal sizeIncrement;

  @JsonProperty("tokenizedEquity")
  private final boolean tokenizedEquity;

  @JsonProperty("type")
  private final String type;

  @JsonProperty("underlying")
  private final String underlying;

  @JsonProperty("volumeUsd24h")
  private final BigDecimal volumeUsd24h;

  @JsonCreator
  public FtxMarketDto(
      @JsonProperty("ask") BigDecimal ask,
      @JsonProperty("baseCurrency") String baseCurrency,
      @JsonProperty("bid") BigDecimal bid,
      @JsonProperty("change1h") BigDecimal change1h,
      @JsonProperty("change24h") BigDecimal change24h,
      @JsonProperty("changeBod") BigDecimal changeBod,
      @JsonProperty("enabled") boolean enabled,
      @JsonProperty("highLeverageFeeExempt") boolean highLeverageFeeExempt,
      @JsonProperty("last") BigDecimal last,
      @JsonProperty("minProvideSize") BigDecimal minProvideSize,
      @JsonProperty("name") String name,
      @JsonProperty("postOnly") boolean postOnly,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("priceIncrement") BigDecimal priceIncrement,
      @JsonProperty("quoteCurrency") String quoteCurrency,
      @JsonProperty("quoteVolume24h") BigDecimal quoteVolume24h,
      @JsonProperty("restricted") boolean restricted,
      @JsonProperty("sizeIncrement") BigDecimal sizeIncrement,
      @JsonProperty("tokenizedEquity") boolean tokenizedEquity,
      @JsonProperty("type") String type,
      @JsonProperty("underlying") String underlying,
      @JsonProperty("volumeUsd24h") BigDecimal volumeUsd24h) {

    this.ask = ask;
    this.baseCurrency = baseCurrency;
    this.bid = bid;
    this.change1h = change1h;
    this.change24h = change24h;
    this.changeBod = changeBod;
    this.enabled = enabled;
    this.highLeverageFeeExempt = highLeverageFeeExempt;
    this.last = last;
    this.minProvideSize = minProvideSize;
    this.name = name;
    this.postOnly = postOnly;
    this.price = price;
    this.priceIncrement = priceIncrement;
    this.quoteCurrency = quoteCurrency;
    this.quoteVolume24h = quoteVolume24h;
    this.restricted = restricted;
    this.sizeIncrement = sizeIncrement;
    this.tokenizedEquity = tokenizedEquity;
    this.type = type;
    this.underlying = underlying;
    this.volumeUsd24h = volumeUsd24h;
  }

  public BigDecimal getAsk() {
    return ask;
  }

  public String getBaseCurrency() {
    return baseCurrency;
  }

  public BigDecimal getBid() {
    return bid;
  }

  public BigDecimal getChange1h() {
    return change1h;
  }

  public BigDecimal getChange24h() {
    return change24h;
  }

  public BigDecimal getChangeBod() {
    return changeBod;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public boolean isHighLeverageFeeExempt() {
    return highLeverageFeeExempt;
  }

  public BigDecimal getLast() {
    return last;
  }

  public BigDecimal getMinProvideSize() {
    return minProvideSize;
  }

  public String getName() {
    return name;
  }

  public boolean isPostOnly() {
    return postOnly;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public BigDecimal getPriceIncrement() {
    return priceIncrement;
  }

  public String getQuoteCurrency() {
    return quoteCurrency;
  }

  public BigDecimal getQuoteVolume24h() {
    return quoteVolume24h;
  }

  public boolean isRestricted() {
    return restricted;
  }

  public BigDecimal getSizeIncrement() {
    return sizeIncrement;
  }

  public boolean isTokenizedEquity() {
    return tokenizedEquity;
  }

  public String getType() {
    return type;
  }

  public String getUnderlying() {
    return underlying;
  }

  public BigDecimal getVolumeUsd24h() {
    return volumeUsd24h;
  }

  @Override
  public String toString() {
    return "FtxMarketDto{"
        + "ask="
        + ask
        + ", baseCurrency='"
        + baseCurrency
        + '\''
        + ", bid="
        + bid
        + ", change1h="
        + change1h
        + ", change24h="
        + change24h
        + ", changeBod="
        + changeBod
        + ", enabled="
        + enabled
        + ", highLeverageFeeExempt="
        + highLeverageFeeExempt
        + ", last="
        + last
        + ", minProvideSize="
        + minProvideSize
        + ", name='"
        + name
        + '\''
        + ", postOnly="
        + postOnly
        + ", price="
        + price
        + ", priceIncrement="
        + priceIncrement
        + ", quoteCurrency='"
        + quoteCurrency
        + '\''
        + ", quoteVolume24h="
        + quoteVolume24h
        + ", restricted="
        + restricted
        + ", sizeIncrement="
        + sizeIncrement
        + ", tokenizedEquity="
        + tokenizedEquity
        + ", type='"
        + type
        + '\''
        + ", underlying='"
        + underlying
        + '\''
        + ", volumeUsd24h="
        + volumeUsd24h
        + '}';
  }
}

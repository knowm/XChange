package org.knowm.xchange.bittrex.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.knowm.xchange.currency.Currency;

public class BittrexV2Market {
  private LocalDateTime created;
  private Boolean isActive;
  private String marketCurrencyLong;
  private String logoUrl;
  private String notice;
  private String baseCurrencyLong;
  private String marketName;
  private BigDecimal minTradeSize;
  private Boolean isSponsored;
  private Currency baseCurrency;
  private Currency marketCurrency;
  private Boolean isRestricted;

  public BittrexV2Market(
      @JsonProperty("Created") String created,
      @JsonProperty("IsActive") Boolean isActive,
      @JsonProperty("MarketCurrencyLong") String marketCurrencyLong,
      @JsonProperty("LogoUrl") String logoUrl,
      @JsonProperty("Notice") String notice,
      @JsonProperty("BaseCurrencyLong") String baseCurrencyLong,
      @JsonProperty("MarketName") String marketName,
      @JsonProperty("MinTradeSize") BigDecimal minTradeSize,
      @JsonProperty("IsSponsored") Boolean isSponsored,
      @JsonProperty("BaseCurrency") Currency baseCurrency,
      @JsonProperty("MarketCurrency") Currency marketCurrency,
      @JsonProperty("IsRestricted") Boolean isRestricted) {
    this.created = LocalDateTime.parse(created);
    this.isActive = isActive;
    this.marketCurrencyLong = marketCurrencyLong;
    this.logoUrl = logoUrl;
    this.notice = notice;
    this.baseCurrencyLong = baseCurrencyLong;
    this.marketName = marketName;
    this.minTradeSize = minTradeSize;
    this.isSponsored = isSponsored;
    this.baseCurrency = baseCurrency;
    this.marketCurrency = marketCurrency;
    this.isRestricted = isRestricted;
  }

  public LocalDateTime getCreated() {
    return created;
  }

  public void setCreated(LocalDateTime created) {
    this.created = created;
  }

  public Boolean getActive() {
    return isActive;
  }

  public void setActive(Boolean active) {
    isActive = active;
  }

  public String getMarketCurrencyLong() {
    return marketCurrencyLong;
  }

  public void setMarketCurrencyLong(String marketCurrencyLong) {
    this.marketCurrencyLong = marketCurrencyLong;
  }

  public String getLogoUrl() {
    return logoUrl;
  }

  public void setLogoUrl(String logoUrl) {
    this.logoUrl = logoUrl;
  }

  public String getNotice() {
    return notice;
  }

  public void setNotice(String notice) {
    this.notice = notice;
  }

  public String getBaseCurrencyLong() {
    return baseCurrencyLong;
  }

  public void setBaseCurrencyLong(String baseCurrencyLong) {
    this.baseCurrencyLong = baseCurrencyLong;
  }

  public String getMarketName() {
    return marketName;
  }

  public void setMarketName(String marketName) {
    this.marketName = marketName;
  }

  public BigDecimal getMinTradeSize() {
    return minTradeSize;
  }

  public void setMinTradeSize(BigDecimal minTradeSize) {
    this.minTradeSize = minTradeSize;
  }

  public Boolean getSponsored() {
    return isSponsored;
  }

  public void setSponsored(Boolean sponsored) {
    isSponsored = sponsored;
  }

  public Currency getBaseCurrency() {
    return baseCurrency;
  }

  public void setBaseCurrency(Currency baseCurrency) {
    this.baseCurrency = baseCurrency;
  }

  public Currency getMarketCurrency() {
    return marketCurrency;
  }

  public void setMarketCurrency(Currency marketCurrency) {
    this.marketCurrency = marketCurrency;
  }

  public Boolean getRestricted() {
    return isRestricted;
  }

  public void setRestricted(Boolean restricted) {
    isRestricted = restricted;
  }

  @Override
  public String toString() {
    return "BittrexV2Market{"
        + "created="
        + created
        + ", isActive="
        + isActive
        + ", marketCurrencyLong='"
        + marketCurrencyLong
        + '\''
        + ", logoUrl='"
        + logoUrl
        + '\''
        + ", notice='"
        + notice
        + '\''
        + ", baseCurrencyLong='"
        + baseCurrencyLong
        + '\''
        + ", marketName='"
        + marketName
        + '\''
        + ", minTradeSize="
        + minTradeSize
        + ", isSponsored="
        + isSponsored
        + ", baseCurrency="
        + baseCurrency
        + ", marketCurrency="
        + marketCurrency
        + ", isRestricted="
        + isRestricted
        + '}';
  }
}

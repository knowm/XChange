package org.knowm.xchange.coinmarketcap.pro.v1.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.math.BigDecimal;
import java.util.Date;
import org.knowm.xchange.utils.jackson.ISO8601DateDeserializer;

public final class CmcQuote {

  private BigDecimal price;
  private BigDecimal volume24h;
  private BigDecimal percentChange1h;
  private BigDecimal percentChange7d;
  private BigDecimal percentChange24h;
  private BigDecimal marketCap;
  private Date lastUpdated;

  public CmcQuote(
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("volume_24h") BigDecimal volume24h,
      @JsonProperty("percent_change_1h") BigDecimal percentChange1h,
      @JsonProperty("percent_change_7d") BigDecimal percentChange7d,
      @JsonProperty("percent_change_24h") BigDecimal percentChange24h,
      @JsonProperty("market_cap") BigDecimal marketCap,
      @JsonProperty("last_updated") @JsonDeserialize(using = ISO8601DateDeserializer.class)
          Date lastUpdated) {

    this.price = price;
    this.volume24h = volume24h;
    this.percentChange1h = percentChange1h;
    this.percentChange7d = percentChange7d;
    this.percentChange24h = percentChange24h;
    this.marketCap = marketCap;
    this.lastUpdated = lastUpdated;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public BigDecimal getVolume24h() {
    return volume24h;
  }

  public BigDecimal getPercentChange1h() {
    return percentChange1h;
  }

  public BigDecimal getPercentChange7d() {
    return percentChange7d;
  }

  public BigDecimal getPercentChange24h() {
    return percentChange24h;
  }

  public BigDecimal getMarketCap() {
    return marketCap;
  }

  public Date getLastUpdated() {
    return lastUpdated;
  }

  @Override
  public String toString() {
    return "CmcQuote{"
        + "price="
        + price
        + ", volume24h="
        + volume24h
        + ", percentChange1h="
        + percentChange1h
        + ", percentChange7d="
        + percentChange7d
        + ", percentChange24h="
        + percentChange24h
        + ", marketCap="
        + marketCap
        + ", lastUpdated='"
        + lastUpdated
        + '\''
        + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    CmcQuote that = (CmcQuote) o;

    if (getPrice() != null ? !getPrice().equals(that.getPrice()) : that.getPrice() != null)
      return false;
    if (getVolume24h() != null
        ? !getVolume24h().equals(that.getVolume24h())
        : that.getVolume24h() != null) return false;
    if (getPercentChange1h() != null
        ? !getPercentChange1h().equals(that.getPercentChange1h())
        : that.getPercentChange1h() != null) return false;
    if (getPercentChange7d() != null
        ? !getPercentChange7d().equals(that.getPercentChange7d())
        : that.getPercentChange7d() != null) return false;
    if (getPercentChange24h() != null
        ? !getPercentChange24h().equals(that.getPercentChange24h())
        : that.getPercentChange24h() != null) return false;
    if (getMarketCap() != null
        ? !getMarketCap().equals(that.getMarketCap())
        : that.getMarketCap() != null) return false;
    return getLastUpdated() != null
        ? getLastUpdated().equals(that.getLastUpdated())
        : that.getLastUpdated() == null;
  }

  @Override
  public int hashCode() {
    int result = getPrice() != null ? getPrice().hashCode() : 0;
    result = 31 * result + (getVolume24h() != null ? getVolume24h().hashCode() : 0);
    result = 31 * result + (getPercentChange1h() != null ? getPercentChange1h().hashCode() : 0);
    result = 31 * result + (getPercentChange7d() != null ? getPercentChange7d().hashCode() : 0);
    result = 31 * result + (getPercentChange24h() != null ? getPercentChange24h().hashCode() : 0);
    result = 31 * result + (getMarketCap() != null ? getMarketCap().hashCode() : 0);
    result = 31 * result + (getLastUpdated() != null ? getLastUpdated().hashCode() : 0);
    return result;
  }
}

package org.knowm.xchange.kucoin.dto.marketdata;

import java.math.BigDecimal;

import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Jan Akerman
 */
public class KucoinTicker {

  private final Currency coinType;
  private final Currency coinTypePair;
  private final CurrencyPair symbol;
  private final boolean trading;
  private final BigDecimal lastDealPrice;
  private final BigDecimal buy;
  private final BigDecimal sell;
  private final BigDecimal change;
  private final BigDecimal sort;
  private final BigDecimal feeRate;
  private final BigDecimal volumeValue;
  private final BigDecimal high;
  private final Long datetime;
  private final BigDecimal vol;
  private final BigDecimal low;
  private final BigDecimal changeRate;

  public KucoinTicker(
      @JsonProperty("coinType") String coinType,
      @JsonProperty("trading") boolean trading,
      @JsonProperty("symbol") String symbol,
      @JsonProperty("lastDealPrice") BigDecimal lastDealPrice,
      @JsonProperty("buy") BigDecimal buy,
      @JsonProperty("sell") BigDecimal sell,
      @JsonProperty("change") BigDecimal change,
      @JsonProperty("coinTypePair") String coinTypePair,
      @JsonProperty("sort") BigDecimal sort,
      @JsonProperty("feeRate") BigDecimal feeRate,
      @JsonProperty("volValue") BigDecimal volumeValue,
      @JsonProperty("high") BigDecimal high,
      @JsonProperty("datetime") Long datetime,
      @JsonProperty("vol") BigDecimal vol,
      @JsonProperty("low") BigDecimal low,
      @JsonProperty("changeRate") BigDecimal changeRate
  ) {
    this.coinType = Currency.getInstance(coinType);
    this.coinTypePair = Currency.getInstance(coinTypePair);
    this.symbol = new CurrencyPair(coinType, coinTypePair);
    this.trading = trading;
    this.lastDealPrice = lastDealPrice;
    this.buy = buy;
    this.sell = sell;
    this.change = change;
    this.sort = sort;
    this.feeRate = feeRate;
    this.volumeValue = volumeValue;
    this.high = high;
    this.datetime = datetime;
    this.vol = vol;
    this.low = low;
    this.changeRate = changeRate;
  }

  public Currency getCoinType() {
    return coinType;
  }

  public boolean isTrading() {
    return trading;
  }

  public CurrencyPair getSymbol() {
    return symbol;
  }

  public BigDecimal getLastDealPrice() {
    return lastDealPrice;
  }

  public BigDecimal getBuy() {
    return buy;
  }

  public BigDecimal getSell() {
    return sell;
  }

  public BigDecimal getChange() {
    return change;
  }

  public Currency getCoinTypePair() {
    return coinTypePair;
  }

  public BigDecimal getSort() {
    return sort;
  }

  public BigDecimal getFeeRate() {
    return feeRate;
  }

  public BigDecimal getVolumeValue() {
    return volumeValue;
  }

  public BigDecimal getHigh() {
    return high;
  }

  public Long getDatetime() {
    return datetime;
  }

  public BigDecimal getVol() {
    return vol;
  }

  public BigDecimal getLow() {
    return low;
  }

  public BigDecimal getChangeRate() {
    return changeRate;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    KucoinTicker that = (KucoinTicker) o;

    if (trading != that.trading) return false;
    if (coinType != null ? !coinType.equals(that.coinType) : that.coinType != null) return false;
    if (coinTypePair != null ? !coinTypePair.equals(that.coinTypePair) : that.coinTypePair != null) return false;
    if (symbol != null ? !symbol.equals(that.symbol) : that.symbol != null) return false;
    if (lastDealPrice != null ? !lastDealPrice.equals(that.lastDealPrice) : that.lastDealPrice != null)
      return false;
    if (buy != null ? !buy.equals(that.buy) : that.buy != null) return false;
    if (sell != null ? !sell.equals(that.sell) : that.sell != null) return false;
    if (change != null ? !change.equals(that.change) : that.change != null) return false;
    if (sort != null ? !sort.equals(that.sort) : that.sort != null) return false;
    if (feeRate != null ? !feeRate.equals(that.feeRate) : that.feeRate != null) return false;
    if (volumeValue != null ? !volumeValue.equals(that.volumeValue) : that.volumeValue != null) return false;
    if (high != null ? !high.equals(that.high) : that.high != null) return false;
    if (datetime != null ? !datetime.equals(that.datetime) : that.datetime != null) return false;
    if (vol != null ? !vol.equals(that.vol) : that.vol != null) return false;
    if (low != null ? !low.equals(that.low) : that.low != null) return false;
    return changeRate != null ? changeRate.equals(that.changeRate) : that.changeRate == null;
  }

  @Override
  public int hashCode() {
    int result = coinType != null ? coinType.hashCode() : 0;
    result = 31 * result + (coinTypePair != null ? coinTypePair.hashCode() : 0);
    result = 31 * result + (symbol != null ? symbol.hashCode() : 0);
    result = 31 * result + (trading ? 1 : 0);
    result = 31 * result + (lastDealPrice != null ? lastDealPrice.hashCode() : 0);
    result = 31 * result + (buy != null ? buy.hashCode() : 0);
    result = 31 * result + (sell != null ? sell.hashCode() : 0);
    result = 31 * result + (change != null ? change.hashCode() : 0);
    result = 31 * result + (sort != null ? sort.hashCode() : 0);
    result = 31 * result + (feeRate != null ? feeRate.hashCode() : 0);
    result = 31 * result + (volumeValue != null ? volumeValue.hashCode() : 0);
    result = 31 * result + (high != null ? high.hashCode() : 0);
    result = 31 * result + (datetime != null ? datetime.hashCode() : 0);
    result = 31 * result + (vol != null ? vol.hashCode() : 0);
    result = 31 * result + (low != null ? low.hashCode() : 0);
    result = 31 * result + (changeRate != null ? changeRate.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "KucoinTicker{" +
        "coinType=" + coinType +
        ", coinTypePair=" + coinTypePair +
        ", symbol=" + symbol +
        ", trading=" + trading +
        ", lastDealPrice=" + lastDealPrice +
        ", buy=" + buy +
        ", sell=" + sell +
        ", change=" + change +
        ", sort=" + sort +
        ", feeRate=" + feeRate +
        ", volumeValue=" + volumeValue +
        ", high=" + high +
        ", datetime=" + datetime +
        ", vol=" + vol +
        ", low=" + low +
        ", changeRate=" + changeRate +
        '}';
  }
}

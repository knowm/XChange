package org.knowm.xchange.coingi.dto.marketdata;

import java.math.BigDecimal;
import java.util.Objects;

/** A currency pair rolling aggregation. */
public class CoingiRollingAggregation {

  private CoingiAggregationCurrencyPair currencyPair;

  private BigDecimal last;

  private BigDecimal lowestAsk;

  private BigDecimal highestBid;

  private BigDecimal baseVolume;

  private BigDecimal counterVolume;

  private BigDecimal high;

  private BigDecimal low;

  public CoingiRollingAggregation(
      CoingiAggregationCurrencyPair currencyPair,
      BigDecimal last,
      BigDecimal lowestAsk,
      BigDecimal highestBid,
      BigDecimal baseVolume,
      BigDecimal counterVolume,
      BigDecimal high,
      BigDecimal low) {
    this.currencyPair = Objects.requireNonNull(currencyPair);
    this.last = Objects.requireNonNull(last);
    this.lowestAsk = Objects.requireNonNull(lowestAsk);
    this.highestBid = Objects.requireNonNull(highestBid);
    this.baseVolume = Objects.requireNonNull(baseVolume);
    this.counterVolume = Objects.requireNonNull(counterVolume);
    this.high = Objects.requireNonNull(high);
    this.low = Objects.requireNonNull(low);
  }

  CoingiRollingAggregation() {}

  public CoingiAggregationCurrencyPair getCurrencyPair() {
    return currencyPair;
  }

  public BigDecimal getLast() {
    return last;
  }

  public BigDecimal getLowestAsk() {
    return lowestAsk;
  }

  public BigDecimal getHighestBid() {
    return highestBid;
  }

  public BigDecimal getBaseVolume() {
    return baseVolume;
  }

  public BigDecimal getCounterVolume() {
    return counterVolume;
  }

  public BigDecimal getHigh() {
    return high;
  }

  public BigDecimal getLow() {
    return low;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CoingiRollingAggregation that = (CoingiRollingAggregation) o;
    return Objects.equals(currencyPair, that.currencyPair)
        && Objects.equals(last, that.last)
        && Objects.equals(lowestAsk, that.lowestAsk)
        && Objects.equals(highestBid, that.highestBid)
        && Objects.equals(baseVolume, that.baseVolume)
        && Objects.equals(counterVolume, that.counterVolume)
        && Objects.equals(high, that.high)
        && Objects.equals(low, that.low);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        currencyPair, last, lowestAsk, highestBid, baseVolume, counterVolume, high, low);
  }
}

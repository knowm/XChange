package org.knowm.xchange.gatecoin.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Sumedha
 */
public final class GatecoinTicker {

  private final String currencyPair;
  private final BigDecimal open;
  private final BigDecimal last;
  private final BigDecimal lastQ;
  private final BigDecimal high;
  private final BigDecimal low;
  private final BigDecimal volume;
  private final BigDecimal volumn;
  private final BigDecimal bid;
  private final BigDecimal ask;
  private final BigDecimal bidQ;
  private final BigDecimal askQ;
  private final BigDecimal vwap;
  private final long createDateTime;

  /**
   * Constructor
   *
   * @param currencyPair
   * @param open
   * @param last
   * @param lastQ
   * @param high
   * @param low
   * @param vwap
   * @param volumn
   * @param volume
   * @param bidQ
   * @param bid
   * @param askQ
   * @param ask
   * @param createDateTime
   */
  public GatecoinTicker(@JsonProperty("currencyPair") String currencyPair, @JsonProperty("open") BigDecimal open,
      @JsonProperty("last") BigDecimal last, @JsonProperty("lastQ") BigDecimal lastQ, @JsonProperty("high") BigDecimal high,
      @JsonProperty("low") BigDecimal low, @JsonProperty("volume") BigDecimal volume, @JsonProperty("volumn") BigDecimal volumn,
      @JsonProperty("bid") BigDecimal bid, @JsonProperty("bidQ") BigDecimal bidQ, @JsonProperty("ask") BigDecimal ask,
      @JsonProperty("askQ") BigDecimal askQ, @JsonProperty("vwap") BigDecimal vwap, @JsonProperty("createDateTime") long createDateTime) {

    this.open = open;
    this.last = last;
    this.lastQ = lastQ;
    this.high = high;
    this.low = low;
    this.vwap = vwap;
    this.volume = volume;
    this.volumn = volumn;
    this.bid = bid;
    this.ask = ask;
    this.bidQ = bidQ;
    this.askQ = askQ;
    this.createDateTime = createDateTime;
    this.currencyPair = currencyPair;
  }

  public BigDecimal getOpen() {

    return open;
  }

  public BigDecimal getLast() {

    return last;
  }

  public BigDecimal getLastQ() {

    return lastQ;
  }

  public BigDecimal getHigh() {

    return high;
  }

  public BigDecimal getLow() {

    return low;
  }

  public BigDecimal getVwap() {

    return vwap;
  }

  public BigDecimal getVolume() {

    return volume;
  }

  public BigDecimal getVolumn() {

    return volumn;
  }

  public BigDecimal getBid() {

    return bid;
  }

  public BigDecimal getAsk() {

    return ask;
  }

  public BigDecimal getBidQ() {

    return bidQ;
  }

  public BigDecimal getAskQ() {

    return askQ;
  }

  public long getTimestamp() {

    return createDateTime;
  }

  public String getCurrencyPair() {

    return currencyPair;
  }

  @Override
  public String toString() {

    return "GatecoinTicker [last=" + last + ",open=" + open + ",volumn=" + volumn + ", high=" + high + ", low=" + low + ", vwap=" + vwap + ", volume="
        + volume + ", bid=" + bid + ", ask=" + ask + ", timestamp=" + createDateTime + "]";
  }

}

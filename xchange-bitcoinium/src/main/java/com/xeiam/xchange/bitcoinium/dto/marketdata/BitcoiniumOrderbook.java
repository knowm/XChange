package com.xeiam.xchange.bitcoinium.dto.marketdata;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data object representing Orderbook from Bitcoinium WebService
 */
public final class BitcoiniumOrderbook {

  private final List<BigDecimal> askPrices;
  private final List<BigDecimal> askVolumes;
  private final List<BigDecimal> bidPrices;
  private final List<BigDecimal> bidVolumes;
  private final BigDecimal high;
  private final BigDecimal low;
  private final BigDecimal bid;
  private final BigDecimal ask;
  private final BigDecimal last;
  private final BigDecimal volume;
  private final long timestamp;

  /**
   * Constructor
   * 
   * @param ap
   * @param av
   * @param bp
   * @param bv
   * @param last
   * @param high
   * @param low
   * @param bid
   * @param ask
   * @param volume
   * @param timestamp
   */
  public BitcoiniumOrderbook(@JsonProperty("ap") List<BigDecimal> ap, @JsonProperty("av") List<BigDecimal> av, @JsonProperty("bp") List<BigDecimal> bp, @JsonProperty("bv") List<BigDecimal> bv,
      @JsonProperty("l") BigDecimal last, @JsonProperty("h") BigDecimal high, @JsonProperty("lo") BigDecimal low, @JsonProperty("b") BigDecimal bid, @JsonProperty("a") BigDecimal ask,
      @JsonProperty("v") BigDecimal volume, @JsonProperty("t") long timestamp) {

    this.askPrices = ap;
    this.askVolumes = av;
    this.bidPrices = bp;
    this.bidVolumes = bv;
    this.last = last;
    this.volume = volume;
    this.high = high;
    this.low = low;
    this.bid = bid;
    this.ask = ask;
    this.timestamp = timestamp;
  }

  public List<BigDecimal> getAskPriceList() {

    return this.askPrices;
  }

  public List<BigDecimal> getAskVolumeList() {

    return this.askVolumes;
  }

  public List<BigDecimal> getBidPriceList() {

    return this.bidPrices;
  }

  public List<BigDecimal> getBidVolumeList() {

    return this.bidVolumes;
  }

  public BigDecimal getLast() {

    return this.last;
  }

  public long getTimestamp() {

    return this.timestamp;
  }

  public BigDecimal getVolume() {

    return this.volume;
  }

  public BigDecimal getHigh() {

    return this.high;
  }

  public BigDecimal getLow() {

    return this.low;
  }

  public BigDecimal getBid() {

    return this.bid;
  }

  public BigDecimal getAsk() {

    return this.ask;
  }

  @Override
  public String toString() {

    return "BitcoiniumOrderbook [askPrice=" + askPrices + ", askVolume=" + askVolumes + ", buyPrice=" + bidPrices + ", buyVolume=" + bidVolumes + ", high=" + high + ", low=" + low + ", bid=" + bid
        + ", ask=" + ask + ", last=" + last + ", volume=" + volume + ", timestamp=" + timestamp + "]";
  }

}

package org.knowm.xchange.coinsetter.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Level 1 market data.
 */
public class CoinsetterTicker {

  private final CoinsetterTrade bid;
  private final CoinsetterTrade ask;
  private final CoinsetterTrade last;
  private final BigDecimal volume;
  private final BigDecimal volume24;

  public CoinsetterTicker(@JsonProperty("bid") CoinsetterTrade bid, @JsonProperty("ask") CoinsetterTrade ask,
      @JsonProperty("last") CoinsetterTrade last, @JsonProperty("volume") BigDecimal volume, @JsonProperty("volume24") BigDecimal volume24) {

    this.bid = bid;
    this.ask = ask;
    this.last = last;
    this.volume = volume;
    this.volume24 = volume24;
  }

  public CoinsetterTrade getBid() {

    return bid;
  }

  public CoinsetterTrade getAsk() {

    return ask;
  }

  public CoinsetterTrade getLast() {

    return last;
  }

  /**
   * Returns the trading volume between midnight ET and the current time ET.
   *
   * @return the trading volume between midnight ET and the current time ET.
   */
  public BigDecimal getVolume() {

    return volume;
  }

  /**
   * Returns the trading volume over the last 24 hours.
   *
   * @return the trading volume over the last 24 hours.
   */
  public BigDecimal getVolume24() {

    return volume24;
  }

  @Override
  public String toString() {

    return "CoinsetterTicker [bid=" + bid + ", ask=" + ask + ", last=" + last + ", volume=" + volume + ", volume24=" + volume24 + "]";
  }

}

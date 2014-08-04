package com.xeiam.xchange.btcchina.dto.marketdata;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data object representing depth from BTCChina
 */
public final class BTCChinaDepth {

  private final BigDecimal[][] asks;
  private final BigDecimal[][] bids;
  private final long date;

  /**
   * Constructor
   * 
   * @param asks
   * @param bids
   */
  public BTCChinaDepth(@JsonProperty("asks") BigDecimal[][] asks, @JsonProperty("bids") BigDecimal[][] bids, @JsonProperty("date") long date) {

    this.asks = asks;
    this.bids = bids;
    this.date = date;
  }

  /**
   * @deprecated Use {@link #getAsksArray()} instead.
   */
  @Deprecated
  public List<BigDecimal[]> getAsks() {

    return Arrays.asList(this.asks);
  }

  public BigDecimal[][] getAsksArray() {

    return this.asks;
  }

  /**
   * @deprecated Use {@link #getBidsArray()} instead.
   */
  @Deprecated
  public List<BigDecimal[]> getBids() {

    return Arrays.asList(this.bids);
  }

  public BigDecimal[][] getBidsArray() {

    return this.bids;
  }

  public long getDate() {

    return date;
  }

  @Override
  public String toString() {

    return "BTCChinaDepth [asks=" + asks.toString() + ", bids=" + bids.toString() + ", date=" + date + "]";
  }
}

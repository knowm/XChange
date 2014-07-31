package com.xeiam.xchange.bitcoinium.dto.marketdata;

import java.math.BigDecimal;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * <p>
 * Data object representing a Ticker History from Bitcoinium Web Service
 * </p>
 */
public final class BitcoiniumTickerHistory {

  /** Price with 3 decimal places of precision */
  private final ArrayList<BigDecimal> priceHistory;

  /** the time stamp (in seconds from epoch) of the oldest data point */
  private final long oldestTimestamp;

  /** the time difference between the previous data point and the current one */
  private final ArrayList<Integer> timestampOffset;

  /**
   * Constructor
   * 
   * @param pp
   * @param t
   * @param tt
   */
  public BitcoiniumTickerHistory(@JsonProperty("pp") ArrayList<BigDecimal> pp, @JsonProperty("t") long t, @JsonProperty("tt") ArrayList<Integer> tt) {

    this.priceHistory = pp;
    this.oldestTimestamp = t;
    this.timestampOffset = tt;
  }

  public ArrayList<BigDecimal> getPriceHistoryList() {

    return this.priceHistory;
  }

  public long getBaseTimestamp() {

    return this.oldestTimestamp;
  }

  public ArrayList<Integer> getTimeStampOffsets() {

    return this.timestampOffset;
  }

  @Override
  public String toString() {

    return "BitcoiniumTickerHistory [priceList=" + priceHistory + ", timestamp=" + oldestTimestamp + ", timeOffsets=" + timestampOffset + "]";
  }

}

package com.xeiam.xchange.bitso.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Piotr Ładyżyński
 */
public class BitsoOrderBook  {

  private final Long timestamp;
  private final List<List<BigDecimal>> bids;
  private final List<List<BigDecimal>> asks;

  /**
   * Constructor
   *
   * @param timestamp
   * @param bids
   * @param asks
   */
  public BitsoOrderBook(@JsonProperty("timestamp") Long timestamp, @JsonProperty("bids") List<List<BigDecimal>> bids,
                           @JsonProperty("asks") List<List<BigDecimal>> asks) {

    this.bids = bids;
    this.asks = asks;
    this.timestamp = timestamp;
  }

  /**
   * @return Timestamp in Unix milliseconds
   */
  public Long getTimestamp() {

    return timestamp;
  }

  /** (price, amount) */
  public List<List<BigDecimal>> getBids() {

    return bids;
  }

  /** (price, amount) */
  public List<List<BigDecimal>> getAsks() {

    return asks;
  }

  @Override
  public String toString() {

    return "BitsoOrderBook [timestamp=" + timestamp + ", bids=" + bids + ", asks=" + asks + "]";
  }


}

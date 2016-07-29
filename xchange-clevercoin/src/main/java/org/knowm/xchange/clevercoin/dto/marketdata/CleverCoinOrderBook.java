package org.knowm.xchange.clevercoin.dto.marketdata;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Karsten Nilsen
 */
public class CleverCoinOrderBook {

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
  public CleverCoinOrderBook(@JsonProperty("timestamp") Long timestamp, @JsonProperty("bids") List<List<BigDecimal>> bids,
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

    return "CleverCoinOrderBook [timestamp=" + timestamp + ", bids=" + bids + ", asks=" + asks + "]";
  }

}

package org.knowm.xchange.taurus.dto.marketdata;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.knowm.xchange.utils.jackson.UnixTimestampDeserializer;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * @author Matija Mazi
 */
public class TaurusOrderBook {

  private final List<List<BigDecimal>> bids;
  private final List<List<BigDecimal>> asks;

  private Date timestamp;

  public TaurusOrderBook(@JsonProperty("bids") List<List<BigDecimal>> bids, @JsonProperty("asks") List<List<BigDecimal>> asks,
      @JsonProperty("timestamp") @JsonDeserialize(using = UnixTimestampDeserializer.class) Date timestamp) {
    this.bids = bids;
    this.asks = asks;
    this.timestamp = timestamp;
  }

  /**
   * (price, amount)
   */
  public List<List<BigDecimal>> getBids() {
    return bids;
  }

  /**
   * (price, amount)
   */
  public List<List<BigDecimal>> getAsks() {
    return asks;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
  }

  @Override
  public String toString() {
    return "TaurusOrderBook [timestamp = " + timestamp + ", bids=" + bids + ", asks=" + asks + "]";
  }
}

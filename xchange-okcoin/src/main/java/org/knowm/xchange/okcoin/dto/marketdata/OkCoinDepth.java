package org.knowm.xchange.okcoin.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

public class OkCoinDepth {

  private final BigDecimal[][] asks;
  private final BigDecimal[][] bids;
  private final Date timestamp;

  public OkCoinDepth(
      @JsonProperty("asks") final BigDecimal[][] asks,
      @JsonProperty("bids") final BigDecimal[][] bids,
      @JsonProperty(required = false, value = "timestamp") Date timestamp) {

    this.asks = asks;
    this.bids = bids;
    this.timestamp = timestamp;
  }

  public BigDecimal[][] getAsks() {

    return asks;
  }

  public BigDecimal[][] getBids() {

    return bids;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  @Override
  public String toString() {

    return "OkCoinDepth [asks=" + Arrays.toString(asks) + ", bids=" + Arrays.toString(bids) + "]";
  }
}

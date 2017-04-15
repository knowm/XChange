package org.knowm.xchange.btcchina.dto.marketdata;

import java.math.BigDecimal;
import java.util.Arrays;

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

  public BigDecimal[][] getAsksArray() {

    return this.asks;
  }

  public BigDecimal[][] getBidsArray() {

    return this.bids;
  }

  public long getDate() {

    return date;
  }

  @Override
  public String toString() {

    return "BTCChinaDepth [asks=" + Arrays.deepToString(asks) + ", bids=" + Arrays.deepToString(bids) + ", date=" + date + "]";
  }
}

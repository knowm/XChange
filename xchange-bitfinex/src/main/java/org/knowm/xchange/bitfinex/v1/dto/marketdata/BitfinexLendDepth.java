package org.knowm.xchange.bitfinex.v1.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Arrays;

public class BitfinexLendDepth {

  private final BitfinexLendLevel[] asks;
  private final BitfinexLendLevel[] bids;

  /**
   * Constructor
   *
   * @param asks
   * @param bids
   */
  public BitfinexLendDepth(
      @JsonProperty("asks") BitfinexLendLevel[] asks,
      @JsonProperty("bids") BitfinexLendLevel[] bids) {

    this.asks = asks;
    this.bids = bids;
  }

  public BitfinexLendLevel[] getAsks() {

    return asks;
  }

  public BitfinexLendLevel[] getBids() {

    return bids;
  }

  @Override
  public String toString() {

    return "BitfinexLendDepth [asks="
        + Arrays.toString(asks)
        + ", bids="
        + Arrays.toString(bids)
        + "]";
  }
}

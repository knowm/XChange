package org.knowm.xchange.bittrex.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Arrays;

public class BittrexDepth {

  private final BittrexLevel[] asks;
  private final BittrexLevel[] bids;

  /**
   * Constructor
   *
   * @param asks
   * @param bids
   */
  public BittrexDepth(
      @JsonProperty("sell") BittrexLevel[] asks, @JsonProperty("buy") BittrexLevel[] bids) {

    this.asks = asks;
    this.bids = bids;
  }

  public BittrexLevel[] getAsks() {

    return asks;
  }

  public BittrexLevel[] getBids() {

    return bids;
  }

  @Override
  public String toString() {

    return "BittrexDepth [asks=" + Arrays.toString(asks) + ", bids=" + Arrays.toString(bids) + "]";
  }
}

package org.knowm.xchange.gemini.v1.dto.marketdata;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GeminiLendDepth {

  private final GeminiLendLevel[] asks;
  private final GeminiLendLevel[] bids;

  /**
   * Constructor
   *
   * @param asks
   * @param bids
   */
  public GeminiLendDepth(@JsonProperty("asks") GeminiLendLevel[] asks, @JsonProperty("bids") GeminiLendLevel[] bids) {

    this.asks = asks;
    this.bids = bids;
  }

  public GeminiLendLevel[] getAsks() {

    return asks;
  }

  public GeminiLendLevel[] getBids() {

    return bids;
  }

  @Override
  public String toString() {

    return "GeminiLendDepth [asks=" + Arrays.toString(asks) + ", bids=" + Arrays.toString(bids) + "]";
  }

}

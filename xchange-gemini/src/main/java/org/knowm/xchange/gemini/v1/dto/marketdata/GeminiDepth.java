package org.knowm.xchange.gemini.v1.dto.marketdata;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GeminiDepth {

  private final GeminiLevel[] asks;
  private final GeminiLevel[] bids;

  /**
   * Constructor
   *
   * @param asks
   * @param bids
   */
  public GeminiDepth(@JsonProperty("asks") GeminiLevel[] asks, @JsonProperty("bids") GeminiLevel[] bids) {

    this.asks = asks;
    this.bids = bids;
  }

  public GeminiLevel[] getAsks() {

    return asks;
  }

  public GeminiLevel[] getBids() {

    return bids;
  }

  @Override
  public String toString() {

    return "GeminiDepth [asks=" + Arrays.toString(asks) + ", bids=" + Arrays.toString(bids) + "]";
  }

}

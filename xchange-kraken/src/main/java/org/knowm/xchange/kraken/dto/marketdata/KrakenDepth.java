package org.knowm.xchange.kraken.dto.marketdata;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data object representing depth from Kraken
 */
public class KrakenDepth {

  private final List<KrakenPublicOrder> asks;
  private final List<KrakenPublicOrder> bids;

  /**
   * Constructor
   *
   * @param asks
   * @param bids
   */
  public KrakenDepth(@JsonProperty("asks") List<KrakenPublicOrder> asks, @JsonProperty("bids") List<KrakenPublicOrder> bids) {

    this.asks = asks;
    this.bids = bids;

  }

  public List<KrakenPublicOrder> getAsks() {

    return asks;
  }

  public List<KrakenPublicOrder> getBids() {

    return bids;
  }

  @Override
  public String toString() {

    return "KrakenDepth [asks=" + asks + ", bids=" + bids + "]";
  }
}

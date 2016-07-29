package org.knowm.xchange.bter.dto.marketdata;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.bter.dto.BTERBaseResponse;

/**
 * Data object representing depth from Bter
 */
public class BTERDepth extends BTERBaseResponse {

  private final List<BTERPublicOrder> asks;
  private final List<BTERPublicOrder> bids;

  /**
   * Constructor
   * 
   * @param asks
   * @param bids
   */
  private BTERDepth(@JsonProperty("asks") List<BTERPublicOrder> asks, @JsonProperty("bids") List<BTERPublicOrder> bids,
      @JsonProperty("result") boolean result) {

    super(result, null);
    this.asks = asks;
    this.bids = bids;
  }

  public List<BTERPublicOrder> getAsks() {

    return asks;
  }

  public List<BTERPublicOrder> getBids() {

    return bids;
  }

  @Override
  public String toString() {

    return "BTERDepth [asks=" + asks.toString() + ", bids=" + bids.toString() + "]";
  }

}

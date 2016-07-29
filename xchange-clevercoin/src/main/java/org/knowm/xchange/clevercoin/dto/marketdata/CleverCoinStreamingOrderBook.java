package org.knowm.xchange.clevercoin.dto.marketdata;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Ryan Sundberg
 */
public class CleverCoinStreamingOrderBook extends CleverCoinOrderBook {

  /**
   * Constructor
   *
   * @param bids
   * @param asks
   */
  public CleverCoinStreamingOrderBook(@JsonProperty("bids") List<List<BigDecimal>> bids, @JsonProperty("asks") List<List<BigDecimal>> asks) {

    super(null, bids, asks);
  }

}

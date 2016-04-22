package org.knowm.xchange.bitstamp.dto.marketdata;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Ryan Sundberg
 */
public class BitstampStreamingOrderBook extends BitstampOrderBook {

  /**
   * Constructor
   *
   * @param bids
   * @param asks
   */
  public BitstampStreamingOrderBook(@JsonProperty("bids") List<List<BigDecimal>> bids, @JsonProperty("asks") List<List<BigDecimal>> asks) {

    super(null, bids, asks);
  }

}

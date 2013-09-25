package org.xchange.kraken.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author Benedikt
 *
 */
public class KrakenTradesResult extends KrakenResult<KrakenTrades> {
  /**
   * 
   * @param error List of errors
   * @param result Recent trades
   */
  public KrakenTradesResult(@JsonProperty("error") String[] error, @JsonProperty("result") KrakenTrades result) {

    super(result, error);
  }

}

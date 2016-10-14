package org.knowm.xchange.kraken.dto.marketdata.results;

import org.knowm.xchange.kraken.dto.KrakenResult;
import org.knowm.xchange.kraken.dto.marketdata.KrakenPublicTrades;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Benedikt
 */
public class KrakenPublicTradesResult extends KrakenResult<KrakenPublicTrades> {

  /**
   * @param error List of errors
   * @param result Recent trades
   */
  public KrakenPublicTradesResult(@JsonProperty("error") String[] error, @JsonProperty("result") KrakenPublicTrades result) {

    super(result, error);
  }

}

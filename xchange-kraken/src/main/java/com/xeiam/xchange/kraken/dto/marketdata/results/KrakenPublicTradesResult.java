package com.xeiam.xchange.kraken.dto.marketdata.results;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.kraken.dto.KrakenResult;
import com.xeiam.xchange.kraken.dto.marketdata.KrakenPublicTrades;

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

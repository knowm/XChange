package org.knowm.xchange.kraken.dto.marketdata.results;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.kraken.dto.KrakenResult;
import org.knowm.xchange.kraken.dto.marketdata.KrakenPublicTrades;

/** @author Benedikt */
public class KrakenPublicTradesResult extends KrakenResult<KrakenPublicTrades> {

  /**
   * @param error List of errors
   * @param result Recent trades
   */
  public KrakenPublicTradesResult(
      @JsonProperty("error") String[] error, @JsonProperty("result") KrakenPublicTrades result) {

    super(result, error);
  }
}

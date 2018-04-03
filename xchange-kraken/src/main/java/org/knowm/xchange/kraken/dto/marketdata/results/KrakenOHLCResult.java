package org.knowm.xchange.kraken.dto.marketdata.results;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.kraken.dto.KrakenResult;
import org.knowm.xchange.kraken.dto.marketdata.KrakenOHLCs;

/** @author Bilgin Ibryam */
public class KrakenOHLCResult extends KrakenResult<KrakenOHLCs> {

  /**
   * Constructor
   *
   * @param result The OHLC data
   * @param error List of errors
   */
  public KrakenOHLCResult(
      @JsonProperty("error") String[] error, @JsonProperty("result") KrakenOHLCs result) {

    super(result, error);
  }
}

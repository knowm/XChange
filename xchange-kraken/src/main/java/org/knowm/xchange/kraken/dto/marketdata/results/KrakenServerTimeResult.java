package org.knowm.xchange.kraken.dto.marketdata.results;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.kraken.dto.KrakenResult;
import org.knowm.xchange.kraken.dto.marketdata.KrakenServerTime;

public class KrakenServerTimeResult extends KrakenResult<KrakenServerTime> {

  /**
   * Constructor
   *
   * @param error
   * @param result
   */
  public KrakenServerTimeResult(
      @JsonProperty("error") String[] error, @JsonProperty("result") KrakenServerTime result) {

    super(result, error);
  }
}

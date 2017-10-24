package org.knowm.xchange.kraken.dto.marketdata.results;

import org.knowm.xchange.kraken.dto.KrakenResult;
import org.knowm.xchange.kraken.dto.marketdata.KrakenServerTime;

import com.fasterxml.jackson.annotation.JsonProperty;

public class KrakenServerTimeResult extends KrakenResult<KrakenServerTime> {

  /**
   * Constructor
   *
   * @param error
   * @param result
   */
  public KrakenServerTimeResult(@JsonProperty("error") String[] error, @JsonProperty("result") KrakenServerTime result) {

    super(result, error);
  }
}

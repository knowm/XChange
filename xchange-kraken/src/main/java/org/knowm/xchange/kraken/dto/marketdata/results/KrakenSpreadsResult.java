package org.knowm.xchange.kraken.dto.marketdata.results;

import org.knowm.xchange.kraken.dto.KrakenResult;
import org.knowm.xchange.kraken.dto.marketdata.KrakenSpreads;

import com.fasterxml.jackson.annotation.JsonProperty;

public class KrakenSpreadsResult extends KrakenResult<KrakenSpreads> {

  /**
   * Constructor
   *
   * @param error List of errors
   * @param result Recent spreads
   */
  public KrakenSpreadsResult(@JsonProperty("error") String[] error, @JsonProperty("result") KrakenSpreads result) {

    super(result, error);
  }
}

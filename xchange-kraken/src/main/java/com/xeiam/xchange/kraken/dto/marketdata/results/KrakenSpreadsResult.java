package com.xeiam.xchange.kraken.dto.marketdata.results;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.kraken.dto.KrakenResult;
import com.xeiam.xchange.kraken.dto.marketdata.KrakenSpreads;

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

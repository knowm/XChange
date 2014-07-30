package com.xeiam.xchange.kraken.dto.marketdata.results;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.kraken.dto.KrakenResult;
import com.xeiam.xchange.kraken.dto.marketdata.KrakenServerTime;

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

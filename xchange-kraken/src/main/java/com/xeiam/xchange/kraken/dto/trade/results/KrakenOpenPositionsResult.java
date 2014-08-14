package com.xeiam.xchange.kraken.dto.trade.results;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.kraken.dto.KrakenResult;
import com.xeiam.xchange.kraken.dto.trade.KrakenOpenPosition;

public class KrakenOpenPositionsResult extends KrakenResult<Map<String, KrakenOpenPosition>> {

  /**
   * Constructor
   * 
   * @param result
   * @param error
   */
  public KrakenOpenPositionsResult(@JsonProperty("result") Map<String, KrakenOpenPosition> result, @JsonProperty("error") String[] error) {

    super(result, error);
  }

}

package com.xeiam.xchange.kraken.dto.trade.results;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.kraken.dto.KrakenResult;
import com.xeiam.xchange.kraken.dto.trade.KrakenOrder;

public class KrakenQueryOrderResult extends KrakenResult<Map<String, KrakenOrder>> {

  /**
   * Constructor
   * 
   * @param result
   * @param error
   */
  public KrakenQueryOrderResult(@JsonProperty("result") Map<String, KrakenOrder> result, @JsonProperty("error") String[] error) {

    super(result, error);
  }

}

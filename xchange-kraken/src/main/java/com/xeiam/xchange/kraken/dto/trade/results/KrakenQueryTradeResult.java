package com.xeiam.xchange.kraken.dto.trade.results;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.kraken.dto.KrakenResult;
import com.xeiam.xchange.kraken.dto.trade.KrakenTrade;

public class KrakenQueryTradeResult extends KrakenResult<Map<String, KrakenTrade>> {

  /**
   * Constructor
   * 
   * @param result
   * @param error
   */
  public KrakenQueryTradeResult(@JsonProperty("result") Map<String, KrakenTrade> result, @JsonProperty("error") String[] error) {

    super(result, error);
  }
}
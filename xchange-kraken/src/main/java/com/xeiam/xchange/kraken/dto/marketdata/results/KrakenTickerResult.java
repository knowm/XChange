package com.xeiam.xchange.kraken.dto.marketdata.results;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.kraken.dto.KrakenResult;
import com.xeiam.xchange.kraken.dto.marketdata.KrakenTicker;

/**
 * @author Raphael Voellmy
 */
public class KrakenTickerResult extends KrakenResult<Map<String, KrakenTicker>> {

  /**
   * Constructor
   * 
   * @param result The ticker data
   * @param error List of errors
   */
  public KrakenTickerResult(@JsonProperty("error") String[] error, @JsonProperty("result") Map<String, KrakenTicker> result) {

    super(result, error);
  }
}

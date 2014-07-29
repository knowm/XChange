package com.xeiam.xchange.kraken.dto.marketdata.results;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.kraken.dto.KrakenResult;
import com.xeiam.xchange.kraken.dto.marketdata.KrakenAssetPair;

public class KrakenAssetPairsResult extends KrakenResult<Map<String, KrakenAssetPair>> {

  /**
   * Constructor
   * 
   * @param result
   * @param error
   */
  public KrakenAssetPairsResult(@JsonProperty("result") Map<String, KrakenAssetPair> result, @JsonProperty("error") String[] error) {

    super(result, error);
  }

}

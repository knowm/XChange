package com.xeiam.xchange.kraken.dto.marketdata.results;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.kraken.dto.KrakenResult;
import com.xeiam.xchange.kraken.dto.marketdata.KrakenAsset;

public class KrakenAssetsResult extends KrakenResult<Map<String, KrakenAsset>> {

  /**
   * Constructor
   * 
   * @param result
   * @param error
   */
  public KrakenAssetsResult(@JsonProperty("result") Map<String, KrakenAsset> result, @JsonProperty("error") String[] error) {

    super(result, error);
  }
}
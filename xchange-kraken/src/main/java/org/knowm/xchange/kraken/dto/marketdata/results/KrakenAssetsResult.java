package org.knowm.xchange.kraken.dto.marketdata.results;

import java.util.Map;

import org.knowm.xchange.kraken.dto.KrakenResult;
import org.knowm.xchange.kraken.dto.marketdata.KrakenAsset;

import com.fasterxml.jackson.annotation.JsonProperty;

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
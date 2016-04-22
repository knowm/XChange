package org.knowm.xchange.kraken.dto.marketdata.results;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.kraken.dto.KrakenResult;
import org.knowm.xchange.kraken.dto.marketdata.KrakenAssetPair;

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

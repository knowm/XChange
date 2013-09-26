package org.xchange.kraken.dto.marketdata;

import java.util.Map;

import org.xchange.kraken.dto.KrakenResult;

import com.fasterxml.jackson.annotation.JsonProperty;

public class KrakenAssetPairsResult extends KrakenResult<Map<String, Object>> {

  public KrakenAssetPairsResult(@JsonProperty("result")Map<String, Object> result,@JsonProperty("error") String[] error) {

    super(result, error);
  }

}

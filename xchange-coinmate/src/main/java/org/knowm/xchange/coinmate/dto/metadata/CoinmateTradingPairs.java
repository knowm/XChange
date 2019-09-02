package org.knowm.xchange.coinmate.dto.metadata;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.coinmate.dto.CoinmateBaseResponse;

public class CoinmateTradingPairs extends CoinmateBaseResponse<CoinmateTradingPairsData> {

  public CoinmateTradingPairs(
      @JsonProperty("error") boolean error,
      @JsonProperty("errorMessage") String errorMessage,
      @JsonProperty("data") CoinmateTradingPairsData data) {

    super(error, errorMessage, data);
  }
}

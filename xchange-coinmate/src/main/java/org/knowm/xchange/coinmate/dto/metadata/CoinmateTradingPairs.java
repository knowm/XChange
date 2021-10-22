package org.knowm.xchange.coinmate.dto.metadata;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.coinmate.dto.CoinmateBaseResponse;

public class CoinmateTradingPairs extends CoinmateBaseResponse<CoinmateTradingPairsEntry[]> {

  public CoinmateTradingPairs(
      @JsonProperty("error") boolean error,
      @JsonProperty("errorMessage") String errorMessage,
      @JsonProperty("data") CoinmateTradingPairsEntry[] data) {

    super(error, errorMessage, data);
  }
}

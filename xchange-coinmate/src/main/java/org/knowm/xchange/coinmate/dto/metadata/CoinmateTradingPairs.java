package org.knowm.xchange.coinmate.dto.metadata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import org.knowm.xchange.coinmate.dto.CoinmateBaseResponse;

public class CoinmateTradingPairs extends CoinmateBaseResponse<List<CoinmateTradingPairsEntry>> {

  public CoinmateTradingPairs(
      @JsonProperty("error") boolean error,
      @JsonProperty("errorMessage") String errorMessage,
      @JsonProperty("data") List<CoinmateTradingPairsEntry> data) {

    super(error, errorMessage, data);
  }
}

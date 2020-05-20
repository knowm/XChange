package org.knowm.xchange.coinmate.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import org.knowm.xchange.coinmate.dto.CoinmateBaseResponse;

public class CoinmateTradeHistory
    extends CoinmateBaseResponse<ArrayList<CoinmateTradeHistoryEntry>> {

  public CoinmateTradeHistory(
      @JsonProperty("error") boolean error,
      @JsonProperty("errorMessage") String errorMessage,
      @JsonProperty("data") ArrayList<CoinmateTradeHistoryEntry> data) {

    super(error, errorMessage, data);
  }
}

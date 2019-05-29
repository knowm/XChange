package org.knowm.xchange.coinmate.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.coinmate.dto.CoinmateBaseResponse;

import java.util.ArrayList;

public class CoinmateTradeHistory extends CoinmateBaseResponse<ArrayList<CoinmateTradeHistoryEntry>> {

  public CoinmateTradeHistory(
      @JsonProperty("error") boolean error,
      @JsonProperty("errorMessage") String errorMessage,
      @JsonProperty("data") ArrayList<CoinmateTradeHistoryEntry> data) {

    super(error, errorMessage, data);
  }
}

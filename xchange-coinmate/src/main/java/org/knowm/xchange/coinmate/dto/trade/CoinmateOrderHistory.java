package org.knowm.xchange.coinmate.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import org.knowm.xchange.coinmate.dto.CoinmateBaseResponse;

public class CoinmateOrderHistory extends CoinmateBaseResponse<List<CoinmateOrderHistoryEntry>> {

  public CoinmateOrderHistory(
      @JsonProperty("error") boolean error,
      @JsonProperty("errorMessage") String errorMessage,
      @JsonProperty("data") List<CoinmateOrderHistoryEntry> data) {

    super(error, errorMessage, data);
  }
}

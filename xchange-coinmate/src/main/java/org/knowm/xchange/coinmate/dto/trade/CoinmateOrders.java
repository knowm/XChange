package org.knowm.xchange.coinmate.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import org.knowm.xchange.coinmate.dto.CoinmateBaseResponse;

public class CoinmateOrders extends CoinmateBaseResponse<List<CoinmateOrderHistoryEntry>> {

  public CoinmateOrders(
      @JsonProperty("error") boolean error,
      @JsonProperty("errorMessage") String errorMessage,
      @JsonProperty("data") List<CoinmateOrderHistoryEntry> data) {

    super(error, errorMessage, data);
  }
}

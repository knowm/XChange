package org.knowm.xchange.coinmate.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.coinmate.dto.CoinmateBaseResponse;

public class CoinmateOrderHistory extends CoinmateBaseResponse<CoinmateOrderHistoryData> {

  public CoinmateOrderHistory(
      @JsonProperty("error") boolean error,
      @JsonProperty("errorMessage") String errorMessage,
      @JsonProperty("data") CoinmateOrderHistoryData data) {

    super(error, errorMessage, data);
  }
}

package org.knowm.xchange.coinmate.dto.trade;

import org.knowm.xchange.coinmate.dto.CoinmateBaseResponse;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CoinmateOrderHistory extends CoinmateBaseResponse<CoinmateOrderHistoryData> {

  public CoinmateOrderHistory(@JsonProperty("error") boolean error, @JsonProperty("errorMessage") String errorMessage,
      @JsonProperty("data") CoinmateOrderHistoryData data) {

    super(error, errorMessage, data);
  }

}

package org.knowm.xchange.coinmate.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.coinmate.dto.CoinmateBaseResponse;

public class CoinmateOrders extends CoinmateBaseResponse<CoinmateOrdersData> {

  public CoinmateOrders(
      @JsonProperty("error") boolean error,
      @JsonProperty("errorMessage") String errorMessage,
      @JsonProperty("data") CoinmateOrdersData data) {

    super(error, errorMessage, data);
  }
}

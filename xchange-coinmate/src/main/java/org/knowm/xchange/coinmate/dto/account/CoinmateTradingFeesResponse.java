package org.knowm.xchange.coinmate.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.coinmate.dto.CoinmateBaseResponse;

public class CoinmateTradingFeesResponse
    extends CoinmateBaseResponse<CoinmateTradingFeesResponseData> {
  public CoinmateTradingFeesResponse(
      @JsonProperty("error") boolean error,
      @JsonProperty("errorMessage") String errorMessage,
      @JsonProperty("data") CoinmateTradingFeesResponseData data) {

    super(error, errorMessage, data);
  }
}

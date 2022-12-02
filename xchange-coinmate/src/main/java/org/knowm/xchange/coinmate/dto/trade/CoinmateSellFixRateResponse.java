package org.knowm.xchange.coinmate.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.coinmate.dto.CoinmateBaseResponse;

public class CoinmateSellFixRateResponse extends CoinmateBaseResponse<CoinmateSellFixRateResponseData> {
  public CoinmateSellFixRateResponse(
      @JsonProperty("error") boolean error,
      @JsonProperty("errorMessage") String errorMessage,
      @JsonProperty("data") CoinmateSellFixRateResponseData data) {

    super(error, errorMessage, data);
  }
}

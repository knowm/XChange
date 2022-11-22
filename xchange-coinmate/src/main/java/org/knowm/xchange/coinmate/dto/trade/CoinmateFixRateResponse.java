package org.knowm.xchange.coinmate.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.coinmate.dto.CoinmateBaseResponse;

public class CoinmateFixRateResponse extends CoinmateBaseResponse<CoinmateFixRateResponseData> {
  public CoinmateFixRateResponse(
      @JsonProperty("error") boolean error,
      @JsonProperty("errorMessage") String errorMessage,
      @JsonProperty("data") CoinmateFixRateResponseData data) {

    super(error, errorMessage, data);
  }
}

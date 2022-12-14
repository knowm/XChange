package org.knowm.xchange.coinmate.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.coinmate.dto.CoinmateBaseResponse;

public class CoinmateBuyFixRateResponse extends CoinmateBaseResponse<CoinmateBuyFixRateResponseData> {
  public CoinmateBuyFixRateResponse(
      @JsonProperty("error") boolean error,
      @JsonProperty("errorMessage") String errorMessage,
      @JsonProperty("data") CoinmateBuyFixRateResponseData data) {

    super(error, errorMessage, data);
  }
}

package org.knowm.xchange.coinmate.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.coinmate.dto.CoinmateBaseResponse;

public class CoinmateReplaceResponse extends CoinmateBaseResponse<CoinmateReplaceResponseData> {

  public CoinmateReplaceResponse(
      @JsonProperty("error") boolean error,
      @JsonProperty("errorMessage") String errorMessage,
      @JsonProperty("data") CoinmateReplaceResponseData data) {

    super(error, errorMessage, data);
  }
}

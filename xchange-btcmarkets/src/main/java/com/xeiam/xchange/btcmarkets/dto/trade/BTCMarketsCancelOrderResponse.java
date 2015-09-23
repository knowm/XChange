package com.xeiam.xchange.btcmarkets.dto.trade;

import java.util.List;

import si.mazi.rescu.ExceptionalReturnContentException;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.btcmarkets.dto.BTCMarketsBaseResponse;
import com.xeiam.xchange.btcmarkets.dto.BTCMarketsException;

public class BTCMarketsCancelOrderResponse extends BTCMarketsBaseResponse {

  public BTCMarketsCancelOrderResponse(
      @JsonProperty("success") Boolean success,
      @JsonProperty("errorMessage") String errorMessage,
      @JsonProperty("errorCode") Integer errorCode,
      @JsonProperty("responses") List<BTCMarketsException> responses
  ) {
    super(success, errorMessage, errorCode);
    if (responses != null) {
      for (BTCMarketsException response : responses) {
        if (!Boolean.TRUE.equals(response.getSuccess())) {
          throw new ExceptionalReturnContentException(String.format("%s: order %d: %s", errorMessage, response.getId(), response.getMessage()));
        }
      }
    }
  }
}

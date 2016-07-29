package org.knowm.xchange.bitfinex.v1.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BitfinexActiveCreditsRequest {

  @JsonProperty("request")
  protected String request;

  @JsonProperty("nonce")
  protected String nonce;

  public BitfinexActiveCreditsRequest(String nonce) {

    this.request = "/v1/credits";
    this.nonce = nonce;
  }
}

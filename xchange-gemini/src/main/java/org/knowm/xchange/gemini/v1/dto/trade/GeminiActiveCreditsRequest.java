package org.knowm.xchange.gemini.v1.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GeminiActiveCreditsRequest {

  @JsonProperty("request")
  protected String request;

  @JsonProperty("nonce")
  protected String nonce;

  public GeminiActiveCreditsRequest(String nonce) {

    this.request = "/v1/credits";
    this.nonce = nonce;
  }
}

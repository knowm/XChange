package org.knowm.xchange.gemini.v1.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GeminiNonceOnlyRequest {

  @JsonProperty("request")
  protected String request;

  @JsonProperty("nonce")
  protected String nonce;

  /**
   * Constructor
   *
   * @param request
   * @param nonce
   */
  public GeminiNonceOnlyRequest(String request, String nonce) {

    this.request = request;
    this.nonce = nonce;
  }
}
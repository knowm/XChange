package org.knowm.xchange.bitfinex.v1.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BitfinexNonceOnlyRequest {

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
  public BitfinexNonceOnlyRequest(String request, String nonce) {

    this.request = request;
    this.nonce = nonce;
  }
}
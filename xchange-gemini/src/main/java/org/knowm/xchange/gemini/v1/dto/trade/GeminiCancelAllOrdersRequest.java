package org.knowm.xchange.gemini.v1.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GeminiCancelAllOrdersRequest {
  @JsonProperty("request")
  protected String request;

  @JsonProperty("nonce")
  protected String nonce;

  @JsonProperty("account")
  private String account;

  /**
   * Constructor
   *
   * @param nonce
   * @param account
   */
  public GeminiCancelAllOrdersRequest(String nonce, String account) {
    this.request = "/v1/order/cancel/all";
    this.nonce = nonce;
    this.account = account;
  }
}

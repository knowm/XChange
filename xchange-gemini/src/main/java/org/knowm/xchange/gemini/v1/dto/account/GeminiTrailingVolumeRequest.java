package org.knowm.xchange.gemini.v1.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GeminiTrailingVolumeRequest {

  @JsonProperty("request")
  protected String request;

  @JsonProperty("nonce")
  protected String nonce;

  /**
   * Constructor
   *
   * @param nonce
   */
  public GeminiTrailingVolumeRequest(String nonce) {

    this.request = "/v1/notionalvolume";
    this.nonce = String.valueOf(nonce);
  }

  public String getRequest() {

    return request;
  }

  public void setRequest(String request) {

    this.request = request;
  }

  public String getNonce() {

    return nonce;
  }

  public void setNonce(String nonce) {

    this.nonce = nonce;
  }
}

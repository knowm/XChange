package org.knowm.xchange.coinone.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CoinoneAuthRequest {

  @JsonProperty("access_token")
  protected String accessTocken;

  @JsonProperty("nonce")
  protected Long nonce;

  @JsonProperty("type")
  protected String type;

  /**
   * Constructor
   *
   * @param nonce
   */
  public CoinoneAuthRequest(String accessTocken, Long nonce, String type) {

    this.accessTocken = accessTocken;
    this.nonce = nonce;
    this.type = type;
  }

  public String getAccessTocken() {
    return accessTocken;
  }

  public void setAccessTocken(String accessTocken) {
    this.accessTocken = accessTocken;
  }

  public Long getNonce() {
    return nonce;
  }

  public void setNonce(Long nonce) {
    this.nonce = nonce;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
}

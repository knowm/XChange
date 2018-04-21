package org.knowm.xchange.coinone.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CoinoneBalancesRequest {

  @JsonProperty("access_token")
  protected String accessTocken;

  @JsonProperty("nonce")
  protected Long nonce;

  /**
   * Constructor
   *
   * @param nonce
   */
  public CoinoneBalancesRequest(String accessTocken, Long nonce) {

    this.accessTocken = accessTocken;
    this.nonce = nonce;
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
}

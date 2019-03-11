package org.knowm.xchange.coingi.dto;

public class CoingiAuthenticatedRequest {
  private String token;
  private Long nonce;
  private String signature;

  public String getToken() {
    return token;
  }

  public CoingiAuthenticatedRequest setToken(String token) {
    this.token = token;
    return this;
  }

  public Long getNonce() {
    return nonce;
  }

  public CoingiAuthenticatedRequest setNonce(Long nonce) {
    this.nonce = nonce;
    return this;
  }

  public String getSignature() {
    return signature;
  }

  public CoingiAuthenticatedRequest setSignature(String signature) {
    this.signature = signature;
    return this;
  }
}

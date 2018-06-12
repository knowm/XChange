package org.knowm.xchange.coingi.dto.request;

class AuthenticatedRequest implements AuthenticatedRequestInterface {
  private String token;
  private Long nonce;
  private String signature;

  @Override
  public String getToken() {
    return token;
  }

  @Override
  public AuthenticatedRequest setToken(String token) {
    this.token = token;
    return this;
  }

  @Override
  public Long getNonce() {
    return nonce;
  }

  @Override
  public AuthenticatedRequest setNonce(Long nonce) {
    this.nonce = nonce;
    return this;
  }

  @Override
  public String getSignature() {
    return signature;
  }

  @Override
  public AuthenticatedRequest setSignature(String signature) {
    this.signature = signature;
    return this;
  }
}

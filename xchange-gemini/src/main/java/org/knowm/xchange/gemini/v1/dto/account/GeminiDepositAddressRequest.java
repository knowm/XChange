package org.knowm.xchange.gemini.v1.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GeminiDepositAddressRequest {
  @JsonProperty("request")
  protected String request;

  @JsonProperty("nonce")
  protected String nonce;

  @JsonProperty("method")
  private String method;

  @JsonProperty("wallet_name")
  private String wallet_name;

  @JsonProperty("renew")
  private int renew;

  public GeminiDepositAddressRequest(String nonce, String method, String wallet_name, int renew) {
    this.request = "/v1/deposit/new";
    this.nonce = String.valueOf(nonce);
    this.method = method;
    this.wallet_name = wallet_name;
    this.renew = renew;
  }

  public String getMethod() {
    return method;
  }

  public void setMethod(String method) {
    this.method = method;
  }

  public String getWallet_name() {
    return wallet_name;
  }

  public void setWallet_name(String wallet_name) {
    this.wallet_name = wallet_name;
  }

  public int getRenew() {
    return renew;
  }

  public void setRenew(int renew) {
    this.renew = renew;
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

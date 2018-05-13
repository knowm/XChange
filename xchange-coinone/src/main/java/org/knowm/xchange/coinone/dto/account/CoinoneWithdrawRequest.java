package org.knowm.xchange.coinone.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CoinoneWithdrawRequest {

  @JsonProperty("access_token")
  protected String accessTocken;

  @JsonProperty("nonce")
  protected Long nonce;

  @JsonProperty("currency")
  protected String currency;

  @JsonProperty("auth_number")
  protected Integer authNumber;

  @JsonProperty("qty")
  protected Double qty;

  @JsonProperty("address")
  protected String address;

  public CoinoneWithdrawRequest(
      String accessTocken,
      Long nonce,
      String currency,
      String authNumber,
      Double qty,
      String address) {

    this.accessTocken = accessTocken;
    this.nonce = nonce;
    this.currency = currency;
    this.authNumber = Integer.parseInt(authNumber);
    this.qty = qty;
    this.address = address;
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

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public Integer getAuthNumber() {
    return authNumber;
  }

  public void setAuthNumber(Integer authNumber) {
    this.authNumber = authNumber;
  }

  public Double getQty() {
    return qty;
  }

  public void setQty(Double qty) {
    this.qty = qty;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }
}

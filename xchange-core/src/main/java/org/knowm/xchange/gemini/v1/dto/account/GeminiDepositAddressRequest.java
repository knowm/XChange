package org.knowm.xchange.gemini.v1.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GeminiDepositAddressRequest {
  @JsonProperty("request")
  private String request;

  @JsonProperty("nonce")
  private String nonce;

  @JsonProperty("label")
  private String label;

  public GeminiDepositAddressRequest(String nonce, String ccy, String label) {
    this.request = "/v1/deposit/" + ccy + "/newAddress";
    this.nonce = String.valueOf(nonce);
    this.label = label;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
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

package org.knowm.xchange.gemini.v1.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GeminiDepositAddressResponse {

  @JsonProperty("result")
  private String result;

  @JsonProperty("method")
  private String method;

  @JsonProperty("currency")
  private String currency;

  @JsonProperty("address")
  private String address;

  public String getResult() {
    return result;
  }

  public void setResult(String result) {
    this.result = result;
  }

  public String getMethod() {
    return method;
  }

  public void setMethod(String method) {
    this.method = method;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }
}

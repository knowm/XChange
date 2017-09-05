package org.knowm.xchange.gemini.v1.dto.account;

import java.math.BigDecimal;

import javax.ws.rs.PathParam;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GeminiWithdrawalRequest {

  @JsonProperty("request")
  public final String request;

  @JsonProperty("nonce")
  public final String nonce;

  @PathParam("currency")
  public final String currency;

  @JsonProperty("amount")
  public final String amount;

  @JsonProperty("address")
  public final String address;

  public GeminiWithdrawalRequest(String nonce, String currency, BigDecimal amount, String address) {
    this.request = "/v1/withdraw/" + currency;
    this.nonce = String.valueOf(nonce);
    this.currency = currency;
    this.amount = amount.toString();
    this.address = address;
  }

  public String getRequest() {

    return request;
  }

  public String getNonce() {

    return nonce;
  }

  public String getCurrency() {
    return currency;
  }

  public String getAmount() {
    return amount;
  }

  public String getAddress() {
    return address;
  }
}
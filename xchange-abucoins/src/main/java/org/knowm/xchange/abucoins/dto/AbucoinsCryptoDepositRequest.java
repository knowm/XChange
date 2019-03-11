package org.knowm.xchange.abucoins.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/** @author bryant_harris */
public class AbucoinsCryptoDepositRequest {
  /** The type of currency */
  @JsonProperty("currency")
  String currency;

  /** Payment method */
  @JsonProperty("method")
  String method;

  public AbucoinsCryptoDepositRequest(String currency, String method) {
    this.currency = currency;
    this.method = method;
  }

  @Override
  public String toString() {
    return "CryptoDepositRequest [currency=" + currency + ", method=" + method + "]";
  }
}

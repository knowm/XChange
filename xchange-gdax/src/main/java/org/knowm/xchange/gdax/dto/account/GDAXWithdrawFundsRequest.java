package org.knowm.xchange.gdax.dto.account;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GDAXWithdrawFundsRequest {
  public final @JsonProperty("amount") BigDecimal amount;
  public final @JsonProperty("currency") String currency;
  public final @JsonProperty("crypto_address") String address;

  public GDAXWithdrawFundsRequest(BigDecimal amount, String currency, String address) {
    this.amount = amount;
    this.currency = currency;
    this.address = address;
  }
}

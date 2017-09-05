package org.knowm.xchange.gdax.dto.account;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GDAXWithdrawCryptoResponse {
  public final String id;
  public final BigDecimal amount;
  public final String currency;

  public GDAXWithdrawCryptoResponse(@JsonProperty("id") String id, @JsonProperty("amount") BigDecimal amount, @JsonProperty("currency") String currency) {
    this.id = id;
    this.amount = amount;
    this.currency = currency;
  }
}

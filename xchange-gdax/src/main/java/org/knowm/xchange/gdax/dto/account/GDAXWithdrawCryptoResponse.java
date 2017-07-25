package org.knowm.xchange.gdax.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

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

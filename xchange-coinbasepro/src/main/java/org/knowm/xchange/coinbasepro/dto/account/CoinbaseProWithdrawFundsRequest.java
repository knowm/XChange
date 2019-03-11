package org.knowm.xchange.coinbasepro.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class CoinbaseProWithdrawFundsRequest {
  public final @JsonProperty("amount") BigDecimal amount;
  public final @JsonProperty("currency") String currency;
  public final @JsonProperty("crypto_address") String address;

  public CoinbaseProWithdrawFundsRequest(BigDecimal amount, String currency, String address) {
    this.amount = amount;
    this.currency = currency;
    this.address = address;
  }
}

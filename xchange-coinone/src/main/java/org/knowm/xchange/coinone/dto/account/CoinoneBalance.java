package org.knowm.xchange.coinone.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class CoinoneBalance {

  private final BigDecimal avail;
  private final BigDecimal balance;

  public CoinoneBalance(
      @JsonProperty("avail") String avail, @JsonProperty("balance") String balance) {
    this.avail = new BigDecimal(avail);
    this.balance = new BigDecimal(balance);
  }

  public BigDecimal getAvail() {
    return avail;
  }

  public BigDecimal getBalance() {
    return balance;
  }
}

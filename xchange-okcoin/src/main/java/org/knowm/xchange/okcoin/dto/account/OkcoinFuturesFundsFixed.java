package org.knowm.xchange.okcoin.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class OkcoinFuturesFundsFixed {

  private final BigDecimal balance;
  private final OkcoinFuturesFunds[] contracts;
  private final BigDecimal rights;

  public OkcoinFuturesFundsFixed(
      @JsonProperty("balance") BigDecimal balance,
      @JsonProperty("contracts") OkcoinFuturesFunds[] contracts,
      @JsonProperty("rights") BigDecimal rights) {

    this.balance = balance;
    this.contracts = contracts;
    this.rights = rights;
  }

  public BigDecimal getBalance() {
    return balance;
  }

  public OkcoinFuturesFunds[] getContracts() {
    return contracts;
  }

  public BigDecimal getRights() {
    return rights;
  }
}

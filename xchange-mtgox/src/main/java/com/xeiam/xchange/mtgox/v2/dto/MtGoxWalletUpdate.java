package com.xeiam.xchange.mtgox.v2.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MtGoxWalletUpdate {

  private final String op;
  private final MtGoxValue balance;
  private final MtGoxValue amount;

  public MtGoxWalletUpdate(@JsonProperty("Balance") MtGoxValue balance, @JsonProperty("op") String op, @JsonProperty("amount") MtGoxValue amount) {

    this.op = op;
    this.balance = balance;
    this.amount = amount;
  }

  public String getOp() {

    return op;
  }

  public MtGoxValue getBalance() {

    return balance;
  }

  public MtGoxValue getAmount() {

    return amount;
  }

  @Override
  public String toString() {

    return "MtGoxWalletUpdate{" + "op='" + op + '\'' + ", balance=" + balance + ", amount=" + amount + '}';
  }
}

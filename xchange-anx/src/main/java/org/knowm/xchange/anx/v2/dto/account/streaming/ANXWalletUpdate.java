package org.knowm.xchange.anx.v2.dto.account.streaming;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.anx.v2.dto.ANXValue;

public class ANXWalletUpdate {

  private final String op;
  private final ANXValue balance;
  private final ANXValue amount;

  /**
   * Constructor
   * 
   * @param balance
   * @param op
   * @param amount
   */
  public ANXWalletUpdate(@JsonProperty("Balance") ANXValue balance, @JsonProperty("op") String op, @JsonProperty("amount") ANXValue amount) {

    this.op = op;
    this.balance = balance;
    this.amount = amount;
  }

  public String getOp() {

    return op;
  }

  public ANXValue getBalance() {

    return balance;
  }

  public ANXValue getAmount() {

    return amount;
  }

  @Override
  public String toString() {

    return "ANXWalletUpdate{" + "op='" + op + '\'' + ", balance=" + balance + ", amount=" + amount + '}';
  }
}

package org.knowm.xchange.bitmarket.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

/** @author kfonal */
public class BitMarketAccountInfo {
  private final BitMarketBalance balance;

  public BitMarketAccountInfo(@JsonProperty("balances") BitMarketBalance balance) {

    this.balance = balance;
  }

  public BitMarketBalance getBalance() {

    return balance;
  }
}

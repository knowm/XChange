package org.knowm.xchange.btcchina.dto.trade;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BTCChinaTransactions {

  private final List<BTCChinaTransaction> transactions;

  public BTCChinaTransactions(@JsonProperty("transaction") List<BTCChinaTransaction> transactions) {

    this.transactions = transactions;
  }

  public List<BTCChinaTransaction> getTransactions() {

    return transactions;
  }

  @Override
  public String toString() {

    return String.format("BTCChinaTransactions{transactions=%s}", transactions);
  }

}

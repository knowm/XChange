package org.knowm.xchange.therock.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * https://api.therocktrading.com/doc/v1/index.html#api-Trading_API-Transactions
 */
public class TheRockTransactions {

  private final TheRockTransaction[] transactions;

  public TheRockTransactions(@JsonProperty("transactions") TheRockTransaction[] transactions, @JsonProperty("meta") Object ignored) {
    this.transactions = transactions;
  }

  public int getCount() {
    return transactions.length;
  }

  public TheRockTransaction[] getTransactions() {
    return transactions;
  }

}

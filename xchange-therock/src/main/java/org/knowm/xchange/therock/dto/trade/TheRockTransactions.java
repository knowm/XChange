package org.knowm.xchange.therock.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

/** https://api.therocktrading.com/doc/v1/index.html#api-Trading_API-Transactions */
public class TheRockTransactions {

  private final TheRockTransaction[] transactions;
  private final Map meta;

  public TheRockTransactions(
      @JsonProperty("transactions") TheRockTransaction[] transactions,
      @JsonProperty("meta") Map meta) {
    this.transactions = transactions;
    this.meta = meta;
  }

  public int getCount() {
    return transactions.length;
  }

  public TheRockTransaction[] getTransactions() {
    return transactions;
  }
}

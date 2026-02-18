package org.knowm.xchange.dase.dto.account;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/** Response wrapper for GET /v1/accounts/transactions. */
public class ApiGetAccountTxnsOutput {

  private final List<ApiAccountTxn> transactions;

  @JsonCreator
  public ApiGetAccountTxnsOutput(@JsonProperty("transactions") List<ApiAccountTxn> transactions) {
    this.transactions = transactions;
  }

  public List<ApiAccountTxn> getTransactions() {
    return transactions;
  }
}

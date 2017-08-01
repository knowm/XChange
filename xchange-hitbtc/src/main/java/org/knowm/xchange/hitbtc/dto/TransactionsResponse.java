package org.knowm.xchange.hitbtc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class TransactionsResponse {
  public final List<TransactionResponse> transactions;

  public TransactionsResponse(@JsonProperty("transactions") List<TransactionResponse> transactions) {
    this.transactions = transactions;
  }
}

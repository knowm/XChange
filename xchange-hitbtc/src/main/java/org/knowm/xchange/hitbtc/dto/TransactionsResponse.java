package org.knowm.xchange.hitbtc.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TransactionsResponse {
  public final List<TransactionResponse> transactions;

  public TransactionsResponse(@JsonProperty("transactions") List<TransactionResponse> transactions) {
    this.transactions = transactions;
  }
}

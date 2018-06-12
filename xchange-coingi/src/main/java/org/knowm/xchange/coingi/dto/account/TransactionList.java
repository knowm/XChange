package org.knowm.xchange.coingi.dto.account;

import org.knowm.xchange.coingi.dto.trade.PaginatedResultList;

import java.util.List;

public class TransactionList extends PaginatedResultList<Transaction> {
  private List<Transaction> transactions;

  public TransactionList(boolean hasMore, List<Transaction> transactions) {
    super(hasMore);
    this.transactions = transactions;
  }

  @Override
  protected List<Transaction> getResultsList() {
    return transactions;
  }
}

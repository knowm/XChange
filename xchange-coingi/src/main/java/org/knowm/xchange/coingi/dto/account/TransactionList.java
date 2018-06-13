package org.knowm.xchange.coingi.dto.account;

import java.util.List;
import org.knowm.xchange.coingi.dto.trade.PaginatedResultList;

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

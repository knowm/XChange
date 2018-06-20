package org.knowm.xchange.coingi.dto.account;

import java.util.List;
import org.knowm.xchange.coingi.dto.trade.CoingiPaginatedResultList;

public class CoingiUserTransactionList extends CoingiPaginatedResultList<CoingiUserTransaction> {
  private List<CoingiUserTransaction> coingiUserTransactions;

  public CoingiUserTransactionList(
      boolean hasMore, List<CoingiUserTransaction> coingiUserTransactions) {
    super(hasMore);
    this.coingiUserTransactions = coingiUserTransactions;
  }

  @Override
  protected List<CoingiUserTransaction> getResultsList() {
    return coingiUserTransactions;
  }
}

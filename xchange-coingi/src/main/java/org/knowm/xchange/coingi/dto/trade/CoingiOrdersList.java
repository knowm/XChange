package org.knowm.xchange.coingi.dto.trade;

import java.util.List;

/** Orders list. */
public class CoingiOrdersList extends PaginatedResultList<CoingiOrder> {
  private List<CoingiOrder> orders;

  public CoingiOrdersList(boolean hasMore, List<CoingiOrder> orders) {
    super(hasMore);
    this.orders = orders;
  }

  @Override
  protected List<CoingiOrder> getResultsList() {
    return orders;
  }
}

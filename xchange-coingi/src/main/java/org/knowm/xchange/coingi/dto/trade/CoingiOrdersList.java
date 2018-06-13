package org.knowm.xchange.coingi.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/** Orders list. */
public class CoingiOrdersList extends CoingiPaginatedResultList<CoingiOrder> {
  private List<CoingiOrder> orders;

  public CoingiOrdersList(
      @JsonProperty("hasMore") boolean hasMore, @JsonProperty("orders") List<CoingiOrder> orders) {
    super(hasMore);
    this.orders = orders;
  }

  @Override
  protected List<CoingiOrder> getResultsList() {
    return orders;
  }
}

package org.knowm.xchange.coinbase.dto.merchant;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import org.knowm.xchange.coinbase.dto.CoinbasePagedResult;

/** @author jamespedwards42 */
public class CoinbaseOrders extends CoinbasePagedResult {

  private final List<CoinbaseOrder> orders;

  private CoinbaseOrders(
      @JsonProperty("orders") final List<CoinbaseOrder> orders,
      @JsonProperty("total_count") final int totalCount,
      @JsonProperty("num_pages") final int numPages,
      @JsonProperty("current_page") final int currentPage) {

    super(totalCount, numPages, currentPage);
    this.orders = orders;
  }

  public List<CoinbaseOrder> getOrders() {

    return orders;
  }

  @Override
  public String toString() {

    return "CoinbaseOrders [orders=" + orders + "]";
  }
}

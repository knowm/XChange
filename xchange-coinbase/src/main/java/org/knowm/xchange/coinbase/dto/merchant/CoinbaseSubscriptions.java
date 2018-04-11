package org.knowm.xchange.coinbase.dto.merchant;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import org.knowm.xchange.coinbase.dto.CoinbasePagedResult;

/** @author jamespedwards42 */
public class CoinbaseSubscriptions extends CoinbasePagedResult {

  private final List<CoinbaseSubscription> subscriptions;

  private CoinbaseSubscriptions(
      @JsonProperty("recurring_payments") final List<CoinbaseSubscription> subscriptions,
      @JsonProperty("total_count") final int totalCount,
      @JsonProperty("num_pages") final int numPages,
      @JsonProperty("current_page") final int currentPage) {

    super(totalCount, numPages, currentPage);
    this.subscriptions = subscriptions;
  }

  public List<CoinbaseSubscription> getSubscriptions() {

    return subscriptions;
  }

  @Override
  public String toString() {

    return "CoinbaseSubscriptions [subscriptions=" + subscriptions + "]";
  }
}

package org.knowm.xchange.coinbase.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import org.knowm.xchange.coinbase.dto.CoinbasePagedResult;

/** @author jamespedwards42 */
public class CoinbaseRecurringPayments extends CoinbasePagedResult {

  private final List<CoinbaseRecurringPayment> recurringPayments;

  private CoinbaseRecurringPayments(
      @JsonProperty("recurring_payments") final List<CoinbaseRecurringPayment> recurringPayments,
      @JsonProperty("total_count") final int totalCount,
      @JsonProperty("num_pages") final int numPages,
      @JsonProperty("current_page") final int currentPage) {

    super(totalCount, numPages, currentPage);
    this.recurringPayments = recurringPayments;
  }

  public List<CoinbaseRecurringPayment> getRecurringPayments() {

    return recurringPayments;
  }

  @Override
  public String toString() {

    return "CoinbaseRecurringPayments [recurringPayments=" + recurringPayments + "]";
  }
}

package org.knowm.xchange.dto.trade;

import java.math.BigDecimal;
import java.util.Date;
import org.knowm.xchange.dto.LoanOrder;
import org.knowm.xchange.dto.Order.OrderType;

/**
 * DTO representing a fixed rate loan order A fixed rate loan order lets you specify a fixed rate
 * for your loan order. When offering loan orders, you should be aware as to whether or not loans
 * have callable or putable provisions. These provisions can serve to be advantageous to either the
 * debtor or the creditor.
 */
public final class FixedRateLoanOrder extends LoanOrder implements Comparable<FixedRateLoanOrder> {

  /** The fixed rate of return for a day */
  private final BigDecimal rate;

  /**
   * @param type Either BID (debtor) or ASK (creditor)
   * @param currency The loan currency code
   * @param originalAmount Units of currency
   * @param dayPeriod Loan duration in days
   * @param id An id (usually provided by the exchange)
   * @param timestamp The absolute time for this order
   * @param rate The fixed rate of return for a day
   */
  public FixedRateLoanOrder(
      OrderType type,
      String currency,
      BigDecimal originalAmount,
      int dayPeriod,
      String id,
      Date timestamp,
      BigDecimal rate) {

    super(type, currency, originalAmount, dayPeriod, id, timestamp);

    this.rate = rate;
  }

  /** @return The fixed rate of return for a day */
  public BigDecimal getRate() {

    return rate;
  }

  @Override
  public int compareTo(FixedRateLoanOrder fixedRateLoanOrder) {

    if (!this.getRate().equals(fixedRateLoanOrder.getRate())) {
      return this.getRate().compareTo(fixedRateLoanOrder.getRate());
    } else {
      return this.getDayPeriod() - fixedRateLoanOrder.getDayPeriod();
    }
  }

  @Override
  public int hashCode() {

    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((rate == null) ? 0 : rate.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {

    if (this == obj) {
      return true;
    }
    if (!super.equals(obj)) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    FixedRateLoanOrder other = (FixedRateLoanOrder) obj;
    if (rate == null) {
      if (other.rate != null) {
        return false;
      }
    } else if (!rate.equals(other.rate)) {
      return false;
    }
    return true;
  }
}

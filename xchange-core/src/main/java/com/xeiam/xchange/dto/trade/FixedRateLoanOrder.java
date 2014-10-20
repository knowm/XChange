/**
 * The MIT License
 * Copyright (c) 2012 Xeiam LLC http://xeiam.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.xeiam.xchange.dto.trade;

import java.math.BigDecimal;
import java.util.Date;

import com.xeiam.xchange.dto.LoanOrder;
import com.xeiam.xchange.dto.Order.OrderType;

/**
 * DTO representing a fixed rate loan order
 * <p>
 * A fixed rate loan order lets you specify a fixed rate for your loan order. When offering loan orders, you should be aware as to whether or not loans have callable or putable provisions. These
 * provisions can serve to be advantageous to either the debtor or the creditor.
 */
public final class FixedRateLoanOrder extends LoanOrder implements Comparable<FixedRateLoanOrder> {

  /**
   * The fixed rate of return for a day
   */
  private final BigDecimal rate;

  /**
   * @param type Either BID (debtor) or ASK (creditor)
   * @param currency The loan currency code
   * @param tradableAmount Units of currency
   * @param dayPeriod Loan duration in days
   * @param id An id (usually provided by the exchange)
   * @param timestamp The absolute time for this order
   * @param rate The fixed rate of return for a day
   */
  public FixedRateLoanOrder(OrderType type, String currency, BigDecimal tradableAmount, int dayPeriod, String id, Date timestamp, BigDecimal rate) {

    super(type, currency, tradableAmount, dayPeriod, id, timestamp);

    this.rate = rate;
  }

  /**
   * @return The fixed rate of return for a day
   */
  public BigDecimal getRate() {

    return rate;
  }

  @Override
  public int compareTo(FixedRateLoanOrder fixedRateLoanOrder) {

    if (!this.getRate().equals(fixedRateLoanOrder.getRate()))
      return this.getRate().compareTo(fixedRateLoanOrder.getRate());
    else
      return this.getDayPeriod() - fixedRateLoanOrder.getDayPeriod();
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

    if (this == obj)
      return true;
    if (!super.equals(obj))
      return false;
    if (getClass() != obj.getClass())
      return false;
    FixedRateLoanOrder other = (FixedRateLoanOrder) obj;
    if (rate == null) {
      if (other.rate != null)
        return false;
    }
    else if (!rate.equals(other.rate))
      return false;
    return true;
  }

}

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
import com.xeiam.xchange.dto.Order;

/**
 * DTO representing a floating rate loan order
 * <p>
 * A floating rate loan order is a loan order whose rate is determined by the market. This type of loan order can be preferable for creditors when
 * loans have a callable provision (i.e. the debtor can choose to pay off the loan early and acquire another loan at a more favorable rate).
 */
public final class FloatingRateLoanOrder extends LoanOrder implements Comparable<FloatingRateLoanOrder> {

  private BigDecimal rate;

  /**
   * @param type Either BID (debtor) or ASK (creditor)
   * @param currency The loan currency code
   * @param tradableAmount Units of currency
   * @param dayPeriod Loan duration in days
   * @param id An id (usually provided by the exchange)
   * @param timestamp The absolute time for this order
   */
  public FloatingRateLoanOrder(Order.OrderType type, String currency, BigDecimal tradableAmount, int dayPeriod, String id, Date timestamp,
      BigDecimal rate) {

    super(type, currency, tradableAmount, dayPeriod, id, timestamp);
    this.rate = rate;
  }

  public BigDecimal getRate() {

    return rate;
  }

  public void setRate(BigDecimal rate) {

    this.rate = rate;
  }

  @Override
  public int compareTo(FloatingRateLoanOrder order) {

    return this.getDayPeriod() - order.getDayPeriod();
  }
}

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

import java.util.List;

/**
 * DTO representing open loan orders
 * <p>
 * Open loan orders are loan order that have placed with the exchange that have not yet been matched to a counterparty.
 */
public final class OpenLoanOrders {

  private final List<FixedRateLoanOrder> openFixedRateLoanOrders;
  private final List<FloatingRateLoanOrder> openFloatingRateLoanOrders;

  /**
   * Constructor
   *
   * @param openFixedRateLoanOrders
   * @param openFloatingRateLoanOrders
   */
  public OpenLoanOrders(List<FixedRateLoanOrder> openFixedRateLoanOrders, List<FloatingRateLoanOrder> openFloatingRateLoanOrders) {

    this.openFixedRateLoanOrders = openFixedRateLoanOrders;
    this.openFloatingRateLoanOrders = openFloatingRateLoanOrders;
  }

  public List<FixedRateLoanOrder> getOpenFixedRateLoanOrders() {

    return openFixedRateLoanOrders;
  }

  public List<FloatingRateLoanOrder> getOpenFloatingRateLoanOrders() {

    return openFloatingRateLoanOrders;
  }

  @Override
  public String toString() {

    return "OpenLoanOrders [openFixedRateLoanOrders=" + openFixedRateLoanOrders + ", openFloatingRateLoanOrders=" + openFloatingRateLoanOrders + "]";
  }

}

/**
 * Copyright (C) 2012 - 2013 Xeiam LLC http://xeiam.com
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.xeiam.xchange.dto.trade;

import java.util.List;

/**
 * <p>
 * DTO representing open orders
 * </p>
 * <p>
 * Open orders are orders that you have placed with the exchange that have not yet been matched to a counter-party.
 * </p>
 */
public final class OpenOrders {

  private final List<LimitOrder> openOrders;

  /**
   * Constructor
   * 
   * @param openOrders The list of open orders
   */
  public OpenOrders(List<LimitOrder> openOrders) {

    this.openOrders = openOrders;
  }

  public List<LimitOrder> getOpenOrders() {

    return openOrders;
  }

  @Override
  public String toString() {

    StringBuilder sb = new StringBuilder();
    if (getOpenOrders().size() < 1) {
      sb.append("No open orders!");
    }
    else {
      sb.append("Open orders: \n");
      for (LimitOrder order : getOpenOrders()) {
        sb.append("[order=");
        sb.append(order.toString());
        sb.append("]\n");
      }
    }
    return sb.toString();
  }

}

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
package com.xeiam.xchange.justcoin.dto.trade.in;

public class OrderReq {

  private final String market;
  private final String price; // The order price. If set to null, the order is placed as a market order.
  private final String amount;
  private final String type;

  // private final String aon = null; // Optional. If true, the order is placed as all-or-nothing. TODO: Is this implemented?

  public OrderReq(String market, String price, String amount, String type) {

    this.market = market;
    this.price = price;
    this.amount = amount;
    this.type = type;
  }

  public String getMarket() {

    return market;
  }

  public String getPrice() {

    return price;
  }

  public String getAmount() {

    return amount;
  }

  public String getType() {

    return type;
  }

}

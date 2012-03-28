/**
 * Copyright (C) 2012 Xeiam LLC http://xeiam.com
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
package com.xeiam.xchange.service.marketdata;

import net.jcip.annotations.Immutable;

/**
 * Data object representing a buy or sell order
 */
@Immutable
public final class Order {

  private final double price;
  private final double amount;
  private final long price_int;
  private final long amount_int;
  private final long stamp;

  /**
   * Constructor
   * 
   * @param price
   * @param amount
   * @param price_int
   * @param amount_int
   * @param stamp
   */
  public Order(double price, double amount, long price_int, long amount_int, long stamp) {

    this.price = price;
    this.amount = amount;
    this.price_int = price_int;
    this.amount_int = amount_int;
    this.stamp = stamp;
  }

  public double getPrice() {
    return price;
  }

  public double getAmount() {
    return amount;
  }

  public long getPrice_int() {
    return price_int;
  }

  public long getAmount_int() {
    return amount_int;
  }

  public long getStamp() {
    return stamp;
  }

  @Override
  public String toString() {
    return "Order [price=" + price + ", amount=" + amount + ", price_int=" + price_int + ", amount_int=" + amount_int + ", stamp=" + stamp + "]";
  }

}

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
package com.xeiam.xchange.dto.marketdata;

import java.util.Date;

import net.jcip.annotations.Immutable;

/**
 * Data object representing a Trade
 */
@Immutable
public final class Trade {

  private final Date date;
  private final long amount_int;
  private final long price_int;
  // TODO refactor to SymbolPair
  private final String price_currency;
  private final String trade_type;

  /**
   * Constructor
   * 
   * @param date
   * @param amount_int
   * @param price_int
   * @param price_currency
   * @param trade_type
   */
  public Trade(Date date, long amount_int, long price_int, String price_currency, String trade_type) {
    this.date = date;
    this.amount_int = amount_int;
    this.price_int = price_int;
    this.price_currency = price_currency;
    this.trade_type = trade_type;
  }

  public Date getDate() {
    return date;
  }

  public long getAmount_int() {
    return amount_int;
  }

  public long getPrice_int() {
    return price_int;
  }

  public String getPrice_currency() {
    return price_currency;
  }

  public String getTrade_type() {
    return trade_type;
  }

  @Override
  public String toString() {
    return "Trade [date=" + date + ", amount_int=" + amount_int + ", price_int=" + price_int + ", price_currency=" + price_currency + ", trade_type=" + trade_type + "]";
  }

}

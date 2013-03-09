/**
 * Copyright (C) 2013 Matija Mazi
 * Copyright (C) 2013 Xeiam LLC http://xeiam.com
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
package com.xeiam.xchange.bitcoincentral.dto.trade;

import java.math.BigDecimal;
import java.text.MessageFormat;

import com.xeiam.xchange.dto.Order;

/**
 * @author Matija Mazi
 */
public abstract class BitcoinCentralTradeBase {

  protected final BigDecimal amount;
  protected final Category category;
  protected final String currency;
  protected final BigDecimal ppc;

  protected BitcoinCentralTradeBase(Category category, String currency, BigDecimal amount, BigDecimal ppc) {

    this.category = category;
    this.currency = currency;
    this.amount = amount;
    this.ppc = ppc;
  }

  public BigDecimal getAmount() {

    return amount;
  }

  public Category getCategory() {

    return category;
  }

  public String getCurrency() {

    return currency;
  }

  @Override
  public String toString() {

    return MessageFormat.format("BitcoinCentralTradeBase[amount={0}, category={1}, currency=''{2}'']", amount, category, currency);
  }

  public BigDecimal getPpc() {

    return ppc;
  }

  public static enum Category {
    buy(Order.OrderType.BID), sell(Order.OrderType.ASK);

    public Order.OrderType type;

    private Category(Order.OrderType type) {

      this.type = type;
    }
  }
}

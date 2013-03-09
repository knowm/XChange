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
import java.util.Date;

/**
 * @author Matija Mazi
 */
public abstract class BitcoinCentralTradeData extends BitcoinCentralTradeBase {

  protected final String createdAt;
  protected final int id;
  protected final Date createdTime;

  public BitcoinCentralTradeData(BigDecimal ppc, Category category, String currency, BigDecimal amount, int id, String createdAt) {

    super(category, currency, amount, ppc);
    this.id = id;
    this.createdAt = createdAt;
    this.createdTime = null;
    // todo: correct pattern for SimpleDateFormat
    // this.createdTime = new SimpleDateFormat("2011-07-05T00:12:07+02:00").parse(createdAt);
  }

  public String getCreatedAt() {

    return createdAt;
  }

  public int getId() {

    return id;
  }

  public Date getCreatedTime() {

    return createdTime;
  }

  @Override
  public String toString() {

    return MessageFormat.format("BitcoinCentralTradeBase[amount={0}, category={1}, currency=''{2}'', price={3}, createdAt=''{4}'', id={5}, createdTime={6}]", amount, category, currency, getPpc(),
        createdAt, id, createdTime);
  }

}

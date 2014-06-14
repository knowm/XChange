/**
 * Copyright (C) 2012 - 2014 Xeiam LLC http://xeiam.com
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
package com.xeiam.xchange.itbit.v1.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ItBitTrade {

  private final BigDecimal amount;
  private final long date;
  private final BigDecimal price;
  private final long tid;

  public ItBitTrade(@JsonProperty("amount") BigDecimal amount, @JsonProperty("date") long date, @JsonProperty("price") BigDecimal price, @JsonProperty("tid") long tid) {

    this.amount = amount;
    this.date = date;
    this.price = price;
    this.tid = tid;
  }

  public BigDecimal getAmount() {

    return amount;
  }

  public long getDate() {

    return date;
  }

  public BigDecimal getPrice() {

    return price;
  }

  public long getTid() {

    return tid;
  }

  @Override
  public String toString() {

    StringBuilder builder = new StringBuilder();
    builder.append("ItBitTrade [amount=");
    builder.append(amount);
    builder.append(", date=");
    builder.append(date);
    builder.append(", price=");
    builder.append(price);
    builder.append(", tid=");
    builder.append(tid);
    builder.append("]");
    return builder.toString();
  }
}

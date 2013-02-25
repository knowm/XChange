/*
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
package com.xeiam.xchange.bitfloor.dto.account;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Matija Mazi
 */
public final class BitfloorBalance implements Serializable {

  private final BigDecimal amount;
  private final BigDecimal hold;
  private final String currency;

  public BitfloorBalance(@JsonProperty("amount") BigDecimal amount, @JsonProperty("hold") BigDecimal hold, @JsonProperty("currency") String currency) {

    this.amount = amount;
    this.hold = hold;
    this.currency = currency;
  }

  public BigDecimal getAmount() {

    return amount;
  }

  public BigDecimal getHold() {

    return hold;
  }

  public String getCurrency() {

    return currency;
  }

  @Override
  public String toString() {

    return String.format("BitfloorBalance{amount=%s, hold=%s, currency='%s'}", amount, hold, currency);
  }
}

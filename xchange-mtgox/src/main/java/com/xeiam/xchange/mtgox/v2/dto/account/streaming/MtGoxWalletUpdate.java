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
package com.xeiam.xchange.mtgox.v2.dto.account.streaming;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.mtgox.v2.dto.MtGoxValue;

public class MtGoxWalletUpdate {

  private final String op;
  private final MtGoxValue balance;
  private final MtGoxValue amount;

  /**
   * Constructor
   * 
   * @param balance
   * @param op
   * @param amount
   */
  public MtGoxWalletUpdate(@JsonProperty("Balance") MtGoxValue balance, @JsonProperty("op") String op, @JsonProperty("amount") MtGoxValue amount) {

    this.op = op;
    this.balance = balance;
    this.amount = amount;
  }

  public String getOp() {

    return op;
  }

  public MtGoxValue getBalance() {

    return balance;
  }

  public MtGoxValue getAmount() {

    return amount;
  }

  @Override
  public String toString() {

    return "MtGoxWalletUpdate{" + "op='" + op + '\'' + ", balance=" + balance + ", amount=" + amount + '}';
  }
}

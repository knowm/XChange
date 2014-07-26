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
package com.xeiam.xchange.anx.v2.dto.account.streaming;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.anx.v2.dto.ANXValue;

public class ANXWalletUpdate {

  private final String op;
  private final ANXValue balance;
  private final ANXValue amount;

  /**
   * Constructor
   * 
   * @param balance
   * @param op
   * @param amount
   */
  public ANXWalletUpdate(@JsonProperty("Balance") ANXValue balance, @JsonProperty("op") String op, @JsonProperty("amount") ANXValue amount) {

    this.op = op;
    this.balance = balance;
    this.amount = amount;
  }

  public String getOp() {

    return op;
  }

  public ANXValue getBalance() {

    return balance;
  }

  public ANXValue getAmount() {

    return amount;
  }

  @Override
  public String toString() {

    return "ANXWalletUpdate{" + "op='" + op + '\'' + ", balance=" + balance + ", amount=" + amount + '}';
  }
}

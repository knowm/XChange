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

/**
 * Data object representing a Wallet from Mt Gox
 */
public final class MtGoxWallet {

  private final MtGoxValue balance;
  private final MtGoxValue dailyWithdrawLimit;
  private final MtGoxValue maxWithdraw;
  private final MtGoxValue monthlyWithdrawLimit;
  private final int operations;

  /**
   * Constructor
   * 
   * @param balance
   * @param dailyWithdrawLimit
   * @param maxWithdraw
   * @param monthlyWithdrawLimit
   * @param operations
   */
  public MtGoxWallet(@JsonProperty("Balance") MtGoxValue balance, @JsonProperty("Daily_Withdraw_Limit") MtGoxValue dailyWithdrawLimit, @JsonProperty("Max_Withdraw") MtGoxValue maxWithdraw,
      @JsonProperty("Monthly_Withdraw_Limit") MtGoxValue monthlyWithdrawLimit, @JsonProperty("Operations") int operations) {

    this.balance = balance;
    this.dailyWithdrawLimit = dailyWithdrawLimit;
    this.maxWithdraw = maxWithdraw;
    this.monthlyWithdrawLimit = monthlyWithdrawLimit;
    this.operations = operations;
  }

  public MtGoxValue getBalance() {

    return this.balance;
  }

  public MtGoxValue getDailyWithdrawLimit() {

    return this.dailyWithdrawLimit;
  }

  public MtGoxValue getMaxWithdraw() {

    return this.maxWithdraw;
  }

  public MtGoxValue getMonthlyWithdrawLimit() {

    return this.monthlyWithdrawLimit;
  }

  public int getOperations() {

    return this.operations;
  }

  @Override
  public String toString() {

    return "MtGoxWallet [balance=" + balance + ", dailyWithdrawLimit=" + dailyWithdrawLimit + ", maxWithdraw=" + maxWithdraw + ", monthlyWithdrawLimit=" + monthlyWithdrawLimit + ", operations="
        + operations + "]";
  }

}

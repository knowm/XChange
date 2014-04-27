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
package com.xeiam.xchange.anx.v2.dto.account.polling;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.anx.v2.dto.ANXValue;

/**
 * Data object representing a Wallet from ANX
 */
public final class ANXWallet {

  private final ANXValue balance;
  private final ANXValue dailyWithdrawLimit;
  private final ANXValue maxWithdraw;
  private final ANXValue monthlyWithdrawLimit;
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
  public ANXWallet(@JsonProperty("Balance") ANXValue balance, @JsonProperty("Daily_Withdrawal_Limit") ANXValue dailyWithdrawLimit, @JsonProperty("Max_Withdraw") ANXValue maxWithdraw,
      @JsonProperty("Monthly_Withdraw_Limit") ANXValue monthlyWithdrawLimit, @JsonProperty("Operations") int operations) {

    this.balance = balance;
    this.dailyWithdrawLimit = dailyWithdrawLimit;
    this.maxWithdraw = maxWithdraw;
    this.monthlyWithdrawLimit = monthlyWithdrawLimit;
    this.operations = operations;
  }

  public ANXValue getBalance() {

    return this.balance;
  }

  public ANXValue getDailyWithdrawLimit() {

    return this.dailyWithdrawLimit;
  }

  public ANXValue getMaxWithdraw() {

    return this.maxWithdraw;
  }

  public ANXValue getMonthlyWithdrawLimit() {

    return this.monthlyWithdrawLimit;
  }

  public int getOperations() {

    return this.operations;
  }

  @Override
  public String toString() {

    return "ANXWallet [balance=" + balance + ", dailyWithdrawLimit=" + dailyWithdrawLimit + ", maxWithdraw=" + maxWithdraw + ", monthlyWithdrawLimit=" + monthlyWithdrawLimit + ", operations="
        + operations + "]";
  }

}

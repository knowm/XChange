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
package com.xeiam.xchange.mtgox.v1.service.trade;

/**
 * Data object representing USD from Mt Gox
 */
public class MtGoxCurrency {

  private Balance balance;
  private Daily_Withdraw_Limit daily_Withdraw_Limit;
  private Max_Withdraw max_Withdraw;
  private Monthly_Withdraw_Limit monthly_Withdraw_Limit;
  private Number operations;

  public Balance getBalance() {
    return this.balance;
  }

  public void setBalance(Balance balance) {
    this.balance = balance;
  }

  public Daily_Withdraw_Limit getDaily_Withdraw_Limit() {
    return this.daily_Withdraw_Limit;
  }

  public void setDaily_Withdraw_Limit(Daily_Withdraw_Limit daily_Withdraw_Limit) {
    this.daily_Withdraw_Limit = daily_Withdraw_Limit;
  }

  public Max_Withdraw getMax_Withdraw() {
    return this.max_Withdraw;
  }

  public void setMax_Withdraw(Max_Withdraw max_Withdraw) {
    this.max_Withdraw = max_Withdraw;
  }

  public Monthly_Withdraw_Limit getMonthly_Withdraw_Limit() {
    return this.monthly_Withdraw_Limit;
  }

  public void setMonthly_Withdraw_Limit(Monthly_Withdraw_Limit monthly_Withdraw_Limit) {
    this.monthly_Withdraw_Limit = monthly_Withdraw_Limit;
  }

  public Number getOperations() {
    return this.operations;
  }

  public void setOperations(Number operations) {
    this.operations = operations;
  }
}

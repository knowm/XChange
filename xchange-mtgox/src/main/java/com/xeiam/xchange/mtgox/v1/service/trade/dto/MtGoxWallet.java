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
package com.xeiam.xchange.mtgox.v1.service.trade.dto;

import org.codehaus.jackson.annotate.JsonProperty;

import com.xeiam.xchange.mtgox.v1.dto.MtGoxValue;

/**
 * Data object representing an Account Currency from Mt Gox
 */
public class MtGoxWallet {

  private MtGoxValue balance = new MtGoxValue();
  private MtGoxValue daily_Withdraw_Limit = new MtGoxValue();
  private MtGoxValue max_Withdraw = new MtGoxValue();
  private MtGoxValue monthly_Withdraw_Limit = new MtGoxValue();
  private int operations;

  @JsonProperty("Balance")
  public MtGoxValue getBalance() {
    return this.balance;
  }

  public void setBalance(MtGoxValue balance) {
    this.balance = balance;
  }

  @JsonProperty("Daily_Withdraw_Limit")
  public MtGoxValue getDaily_Withdraw_Limit() {
    return this.daily_Withdraw_Limit;
  }

  public void setDaily_Withdraw_Limit(MtGoxValue daily_Withdraw_Limit) {
    this.daily_Withdraw_Limit = daily_Withdraw_Limit;
  }

  @JsonProperty("Max_Withdraw")
  public MtGoxValue getMax_Withdraw() {
    return this.max_Withdraw;
  }

  public void setMax_Withdraw(MtGoxValue max_Withdraw) {
    this.max_Withdraw = max_Withdraw;
  }

  @JsonProperty("Monthly_Withdraw_Limit")
  public MtGoxValue getMonthly_Withdraw_Limit() {
    return this.monthly_Withdraw_Limit;
  }

  public void setMonthly_Withdraw_Limit(MtGoxValue monthly_Withdraw_Limit) {
    this.monthly_Withdraw_Limit = monthly_Withdraw_Limit;
  }

  @JsonProperty("Operations")
  public int getOperations() {
    return this.operations;
  }

  public void setOperations(int operations) {
    this.operations = operations;
  }

  @Override
  public String toString() {
    return "MtGoxWallet [balance=" + balance + ", daily_Withdraw_Limit=" + daily_Withdraw_Limit + ", max_Withdraw=" + max_Withdraw + ", monthly_Withdraw_Limit=" + monthly_Withdraw_Limit + ", operations=" + operations
        + "]";
  }

}

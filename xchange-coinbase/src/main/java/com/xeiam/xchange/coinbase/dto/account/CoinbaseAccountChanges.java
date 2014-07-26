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
package com.xeiam.xchange.coinbase.dto.account;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.coinbase.dto.CoinbasePagedResult;
import com.xeiam.xchange.coinbase.dto.account.CoinbaseUser.CoinbaseUserInfo;
import com.xeiam.xchange.coinbase.dto.marketdata.CoinbaseMoney;

/**
 * @author jamespedwards42
 */
public class CoinbaseAccountChanges extends CoinbasePagedResult {

  private final CoinbaseUser currentUser;
  private final CoinbaseMoney balance;
  private final List<CoinbaseAccountChange> accountChanges;

  private CoinbaseAccountChanges(@JsonProperty("current_user") final CoinbaseUserInfo currentUser, @JsonProperty("balance") final CoinbaseMoney balance,
      @JsonProperty("account_changes") final List<CoinbaseAccountChange> accountChanges, @JsonProperty("total_count") final int totalCount, @JsonProperty("num_pages") final int numPages,
      @JsonProperty("current_page") final int currentPage) {

    super(totalCount, numPages, currentPage);
    this.currentUser = new CoinbaseUser(currentUser);
    this.balance = balance;
    this.accountChanges = accountChanges;
  }

  public CoinbaseUser getCurrentUser() {

    return currentUser;
  }

  public CoinbaseMoney getBalance() {

    return balance;
  }

  public List<CoinbaseAccountChange> getAccountChanges() {

    return accountChanges;
  }

  @Override
  public String toString() {

    return "CoinbaseAccountChanges [currentUser=" + currentUser + ", balance=" + balance + ", accountChanges=" + accountChanges + "]";
  }

}

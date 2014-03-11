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
package com.xeiam.xchange.cryptotrade.dto.account;

import java.math.BigDecimal;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.cryptotrade.dto.CryptoTradeBaseResponse;

public class CryptoTradeAccountInfo extends CryptoTradeBaseResponse {

  private final CryptoTradeAccountData accountData;

  private CryptoTradeAccountInfo(@JsonProperty("data") CryptoTradeAccountData accountData, @JsonProperty("status") String status, @JsonProperty("error") String error) {

    super(status, error);
    this.accountData = accountData;
  }

  public Map<String, BigDecimal> getFunds() {

    return accountData.getFunds();
  }

  public CryptoTradeAccountPermissions getPermissions() {

    return accountData.getPermissions();
  }

  public int getActiveOrders() {

    return accountData.getActiveOrders();
  }

  public int getTransactionCount() {

    return accountData.getTransactionCount();
  }

  public long getServerTimestamp() {

    return accountData.getServerTimestamp();
  }

  @Override
  public String toString() {

    return "CryptoTradeAccountInfoReturn [accountData=" + accountData + "]";
  }

  private static class CryptoTradeAccountData {

    private final Map<String, BigDecimal> funds;
    private final CryptoTradeAccountPermissions permissions;
    private final int activeOrders;
    private final int transactionCount;
    private final long serverTimestamp;

    private CryptoTradeAccountData(@JsonProperty("funds") Map<String, BigDecimal> funds, @JsonProperty("perms") CryptoTradeAccountPermissions permissions,
        @JsonProperty("active_orders") int activeOrders, @JsonProperty("transactions_count") int transactionCount, @JsonProperty("server_timestamp") long serverTimestamp) {

      this.funds = funds;
      this.permissions = permissions;
      this.activeOrders = activeOrders;
      this.transactionCount = transactionCount;
      this.serverTimestamp = serverTimestamp;
    }

    public Map<String, BigDecimal> getFunds() {

      return funds;
    }

    public CryptoTradeAccountPermissions getPermissions() {

      return permissions;
    }

    public int getActiveOrders() {

      return activeOrders;
    }

    public int getTransactionCount() {

      return transactionCount;
    }

    public long getServerTimestamp() {

      return serverTimestamp;
    }

    @Override
    public String toString() {

      return "CryptoTradeAccountData [funds=" + funds + ", permissions=" + permissions + ", activeOrders=" + activeOrders + ", transactionCount=" + transactionCount + ", serverTimestamp="
          + serverTimestamp + "]";
    }
  }

  public static class CryptoTradeAccountPermissions {

    private final int info;
    private final int history;
    private final int trade;

    private CryptoTradeAccountPermissions(@JsonProperty("info") int info, @JsonProperty("history") int history, @JsonProperty("trade") int trade) {

      this.info = info;
      this.history = history;
      this.trade = trade;
    }

    public int getInfo() {

      return info;
    }

    public int getHistory() {

      return history;
    }

    public int getTrade() {

      return trade;
    }

    @Override
    public String toString() {

      return "CryptoTradeAccountPermissions [info=" + info + ", history=" + history + ", trade=" + trade + "]";
    }

  }
}

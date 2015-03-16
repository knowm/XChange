package com.xeiam.xchange.cryptotrade.dto.account;

import java.math.BigDecimal;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.cryptotrade.dto.CryptoTradeBaseResponse;

public class CryptoTradeAccountInfo extends CryptoTradeBaseResponse {

  private final CryptoTradeAccountData accountData;

  private CryptoTradeAccountInfo(@JsonProperty("data") CryptoTradeAccountData accountData, @JsonProperty("status") String status,
      @JsonProperty("error") String error) {

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

    private CryptoTradeAccountData(@JsonProperty("funds") Map<String, BigDecimal> funds,
        @JsonProperty("perms") CryptoTradeAccountPermissions permissions, @JsonProperty("active_orders") int activeOrders,
        @JsonProperty("transactions_count") int transactionCount, @JsonProperty("server_timestamp") long serverTimestamp) {

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

      return "CryptoTradeAccountData [funds=" + funds + ", permissions=" + permissions + ", activeOrders=" + activeOrders + ", transactionCount="
          + transactionCount + ", serverTimestamp=" + serverTimestamp + "]";
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

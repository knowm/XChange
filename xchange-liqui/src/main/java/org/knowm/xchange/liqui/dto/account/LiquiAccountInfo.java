package org.knowm.xchange.liqui.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LiquiAccountInfo {

  private final LiquiAccountFunds funds;
  private final long transactionCount;
  private final long openOrders;
  private final long serverTime;
  private final LiquiAccountRights rights;

  public LiquiAccountInfo(
      @JsonProperty("funds") final LiquiAccountFunds funds,
      @JsonProperty("transaction_count") final long transactionCount,
      @JsonProperty("open_orders") final long openOrders,
      @JsonProperty("server_time") final long serverTime,
      @JsonProperty("rights") final LiquiAccountRights rights) {
    this.funds = funds;
    this.transactionCount = transactionCount;
    this.openOrders = openOrders;
    this.serverTime = serverTime;
    this.rights = rights;
  }

  public LiquiAccountFunds getFunds() {
    return funds;
  }

  public long getTransactionCount() {
    return transactionCount;
  }

  public long getOpenOrders() {
    return openOrders;
  }

  public long getServerTime() {
    return serverTime;
  }

  public LiquiAccountRights getRights() {
    return rights;
  }

  @Override
  public String toString() {
    return "LiquiAccountInfo{"
        + "funds="
        + funds
        + ", transactionCount="
        + transactionCount
        + ", openOrders="
        + openOrders
        + ", serverTime="
        + serverTime
        + ", rights="
        + rights
        + '}';
  }
}

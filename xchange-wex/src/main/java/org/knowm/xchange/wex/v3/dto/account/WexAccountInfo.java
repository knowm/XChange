package org.knowm.xchange.wex.v3.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Map;

/** @author Matija Mazi */
public class WexAccountInfo {

  private final int transactionCount; // deprecated, always 0
  private final int openOrders;
  private final long serverTime;
  private final Rights rights;
  private final Map<String, BigDecimal> funds;

  /**
   * Constructor
   *
   * @param transactionCount The number of transactions
   * @param openOrders The open orders
   * @param serverTime The server time (Unix time)
   * @param rights The rights
   * @param funds The funds
   */
  public WexAccountInfo(
      @JsonProperty("transaction_count") int transactionCount,
      @JsonProperty("open_orders") int openOrders,
      @JsonProperty("server_time") long serverTime,
      @JsonProperty("rights") Rights rights,
      @JsonProperty("funds") Map<String, BigDecimal> funds) {

    this.transactionCount = transactionCount;
    this.openOrders = openOrders;
    this.serverTime = serverTime;
    this.rights = rights;
    this.funds = funds;
  }

  @Deprecated
  public int getTransactionCount() {

    return transactionCount;
  }

  public int getOpenOrders() {

    return openOrders;
  }

  public long getServerTime() {

    return serverTime;
  }

  public Rights getRights() {

    return rights;
  }

  public Map<String, BigDecimal> getFunds() {

    return funds;
  }

  @Override
  public String toString() {

    return MessageFormat.format(
        "WexAccountInfo[transactionCount={0}, openOrders={1}, serverTime={2}, rights={3}, funds=''{4}''']",
        transactionCount, openOrders, serverTime, rights, funds);
  }

  public static class Rights {

    private final boolean info, trade, withdraw;

    /**
     * Constructor
     *
     * @param info
     * @param trade
     * @param withdraw
     */
    private Rights(
        @JsonProperty("info") boolean info,
        @JsonProperty("trade") boolean trade,
        @JsonProperty("withdraw") boolean withdraw) {

      this.info = info;
      this.trade = trade;
      this.withdraw = withdraw;
    }

    public boolean isInfo() {

      return info;
    }

    public boolean isTrade() {

      return trade;
    }

    public boolean isWithdraw() {

      return withdraw;
    }

    @Override
    public String toString() {

      return MessageFormat.format(
          "Rights[info={0}, trade={1}, withdraw={2}]", info, trade, withdraw);
    }
  }
}

package org.knowm.xchange.dsx.dto.account;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Mikhail Wall
 */

public class DSXAccountInfo {

  private final int transactionCount;
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
  public DSXAccountInfo(@JsonProperty("transaction_count") int transactionCount, @JsonProperty("open_orders") int openOrders,
      @JsonProperty("server_time") long serverTime, @JsonProperty("rights") Rights rights, @JsonProperty("funds") Map<String, BigDecimal> funds) {

    this.transactionCount = transactionCount;
    this.openOrders = openOrders;
    this.serverTime = serverTime;
    this.rights = rights;
    this.funds = funds;
  }

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

    return MessageFormat.format("DSXAccountInfo[transactionCount={0}, openOrderes={1}, serverTime={2}, rights={3}, funds=''{4}''']",
        transactionCount, openOrders, serverTime, rights, funds);
  }

  public static class Rights {

    private final boolean info, trade;

    private Rights(@JsonProperty("info") boolean info, @JsonProperty("trade") boolean trade) {

      this.info = info;
      this.trade = trade;
    }

    public boolean isInfo() {

      return info;
    }

    public boolean isTrade() {

      return trade;
    }

    @Override
    public String toString() {

      return MessageFormat.format("Rights[info={0}, trade={1}, withdraw={2}]", info, trade);
    }
  }
}

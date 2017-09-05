package org.knowm.xchange.dsx.dto.account;

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
  private final Map<String, DSXCurrencyAmount> funds;

  /**
   * Constructor
   *
   * @param transactionCount The number of transactions
   * @param openOrders The open orders
   * @param serverTime The server time (Unix time)
   * @param rights The rights
   * @param funds The funds
   */
  public DSXAccountInfo(@JsonProperty("transactionCount") int transactionCount, @JsonProperty("openOrders") int openOrders,
      @JsonProperty("serverTime") long serverTime, @JsonProperty("rights") Rights rights,
      @JsonProperty("funds") Map<String, DSXCurrencyAmount> funds) {

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

  public Map<String, DSXCurrencyAmount> getFunds() {

    return funds;
  }

  @Override
  public String toString() {

    return MessageFormat.format("DSXAccountInfo[transactionCount={0}, openOrders={1}, serverTime={2}, rights={3}, funds=''{4}''', total={5}]",
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

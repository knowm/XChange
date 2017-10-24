package org.knowm.xchange.bitmarket.dto.trade;

import org.knowm.xchange.bitmarket.dto.account.BitMarketBalance;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author kfonal
 */
public class BitMarketTrade {

  private final long id;
  private final BitMarketOrder order;
  private final BitMarketBalance balance;

  /**
   * Constructor
   *
   * @param id
   * @param order
   * @param balance
   */
  public BitMarketTrade(@JsonProperty("id") long id, @JsonProperty("order") BitMarketOrder order,
      @JsonProperty("balances") BitMarketBalance balance) {

    this.id = id;
    this.order = order;
    this.balance = balance;
  }

  public long getId() {
    return id;
  }

  public BitMarketOrder getOrder() {
    return order;
  }

  public BitMarketBalance getBalance() {
    return balance;
  }
}

package com.xeiam.xchange.bitmarket.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.bitmarket.dto.account.BitMarketAccount;

/**
 * Created with IntelliJ IDEA.
 * User: Yaroslav
 * Date: 24/04/15
 * Time: 16:12
 */
public class BitMarketOrderResponse {

  private final String id;
  private final BitMarketOrder order;
  private final BitMarketAccount.AccountBalance balances;

  public BitMarketOrderResponse(@JsonProperty("id") String id,
                                @JsonProperty("order") BitMarketOrder order,
                                @JsonProperty("balances") BitMarketAccount.AccountBalance balances) {
    this.id = id;
    this.order = order;
    this.balances = balances;
  }

  public String getId() {
    return id;
  }

  public BitMarketOrder getOrder() {
    return order;
  }

  public BitMarketAccount.AccountBalance getBalances() {
    return balances;
  }
}

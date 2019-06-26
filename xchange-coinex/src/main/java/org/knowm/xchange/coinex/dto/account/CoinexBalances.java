package org.knowm.xchange.coinex.dto.account;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.Map;

public class CoinexBalances {
  private final Map<String, CoinexBalanceInfo> balances;

  @JsonCreator
  public CoinexBalances(Map<String, CoinexBalanceInfo> balances) {
    this.balances = balances;
  }

  public Map<String, CoinexBalanceInfo> getBalances() {
    return balances;
  }

  @Override
  public String toString() {
    return "CoinexBalances{" + "balances=" + balances + '}';
  }
}

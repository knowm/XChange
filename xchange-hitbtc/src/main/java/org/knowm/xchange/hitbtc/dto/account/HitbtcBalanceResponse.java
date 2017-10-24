package org.knowm.xchange.hitbtc.dto.account;

import java.util.Arrays;

import org.knowm.xchange.hitbtc.dto.HitbtcBaseResponse;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HitbtcBalanceResponse extends HitbtcBaseResponse {

  private final HitbtcBalance[] balances;

  public HitbtcBalanceResponse(@JsonProperty("balance") HitbtcBalance[] balances) {

    this.balances = balances;
  }

  public HitbtcBalance[] getBalances() {

    return balances;
  }

  @Override
  public String toString() {

    StringBuilder builder = new StringBuilder();
    builder.append("HitbtcBalanceResponse [balances=");
    builder.append(Arrays.toString(balances));
    builder.append("]");
    return builder.toString();
  }
}

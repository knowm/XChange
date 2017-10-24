package org.knowm.xchange.hitbtc.dto.account;

import java.util.Arrays;

import org.knowm.xchange.hitbtc.dto.HitbtcBaseResponse;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HitbtcPaymentBalanceResponse extends HitbtcBaseResponse {

  private final HitbtcPaymentBalance[] balances;

  public HitbtcPaymentBalanceResponse(@JsonProperty("balance") HitbtcPaymentBalance[] balances) {
    this.balances = balances;
  }

  public HitbtcPaymentBalance[] getBalances() {
    return balances;
  }

  @Override
  public String toString() {
    return "HitbtcPaymentBalanceResponse{" +
        "balances=" + Arrays.toString(balances) +
        '}';
  }
}

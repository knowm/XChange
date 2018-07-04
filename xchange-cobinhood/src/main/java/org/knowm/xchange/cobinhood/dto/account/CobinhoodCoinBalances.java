package org.knowm.xchange.cobinhood.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class CobinhoodCoinBalances {
  private final List<CobinhoodCoinBalance> balances;

  public CobinhoodCoinBalances(@JsonProperty("balances") List<CobinhoodCoinBalance> balances) {
    this.balances = balances;
  }

  public List<CobinhoodCoinBalance> getBalances() {
    return balances;
  }
}

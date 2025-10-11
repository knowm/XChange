package org.knowm.xchange.dase.dto.account;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class DaseBalancesResponse {

  private final List<DaseBalanceItem> balances;

  @JsonCreator
  public DaseBalancesResponse(@JsonProperty("balances") List<DaseBalanceItem> balances) {
    this.balances = balances;
  }

  public List<DaseBalanceItem> getBalances() {
    return balances;
  }
}

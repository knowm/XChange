package org.knowm.xchange.mexc.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class MEXCBalances {

  private final Map<String, MEXCBalance> balances;

  public MEXCBalances(@JsonProperty("data")Map<String, MEXCBalance> balances) {
    this.balances = balances;
  }

  public Map<String, MEXCBalance> getBalances() {
    return balances;
  }

}

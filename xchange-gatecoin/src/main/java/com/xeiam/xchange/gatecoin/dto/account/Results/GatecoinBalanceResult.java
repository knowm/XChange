package com.xeiam.xchange.gatecoin.dto.account.Results;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.gatecoin.dto.GatecoinResult;
import com.xeiam.xchange.gatecoin.dto.account.GatecoinBalance;
import com.xeiam.xchange.gatecoin.dto.marketdata.ResponseStatus;

/**
 * @author sumedha
 */
public class GatecoinBalanceResult extends GatecoinResult {
  private final GatecoinBalance[] balances;

  public GatecoinBalanceResult(
      @JsonProperty("balances") GatecoinBalance[] balances,
      @JsonProperty("responseStatus") ResponseStatus responseStatus
  ) {
    super(responseStatus);
    this.balances = balances;
  }

  public GatecoinBalance[] getBalances() {
    return balances;
  }
}

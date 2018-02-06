package org.knowm.xchange.okcoin.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OkCoinInfo {

  private final OkCoinFunds funds;

  public OkCoinInfo(@JsonProperty("funds") final OkCoinFunds funds) {

    this.funds = funds;
  }

  public OkCoinFunds getFunds() {

    return funds;
  }
}

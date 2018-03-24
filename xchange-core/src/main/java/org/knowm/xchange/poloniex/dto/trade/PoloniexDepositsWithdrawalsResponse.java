package org.knowm.xchange.poloniex.dto.trade;

import java.util.List;

import org.knowm.xchange.exceptions.ExchangeException;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PoloniexDepositsWithdrawalsResponse {

  private final List<PoloniexDeposit> deposits;
  private final List<PoloniexWithdrawal> withdrawals;

  @JsonCreator
  public PoloniexDepositsWithdrawalsResponse(@JsonProperty("error") String error, @JsonProperty("deposits") List<PoloniexDeposit> deposits,
      @JsonProperty("withdrawals") List<PoloniexWithdrawal> withdrawals) {
    if (error != null) {
      throw new ExchangeException(error);
    }
    this.deposits = deposits;
    this.withdrawals = withdrawals;
  }

  public List<PoloniexDeposit> getDeposits() {
    return deposits;
  }

  public List<PoloniexWithdrawal> getWithdrawals() {
    return withdrawals;
  }

  @Override
  public String toString() {
    return "DepositsWithdrawalsResponse [deposits=" + deposits + ", withdrawals=" + withdrawals + "]";
  }
}

package org.knowm.xchange.poloniex.dto.trade;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import org.knowm.xchange.exceptions.ExchangeException;

public class PoloniexDepositsWithdrawalsResponse {

  private final List<PoloniexAdjustment> adjustments;
  private final List<PoloniexDeposit> deposits;
  private final List<PoloniexWithdrawal> withdrawals;

  @JsonCreator
  public PoloniexDepositsWithdrawalsResponse(
      @JsonProperty("error") String error,
      @JsonProperty("adjustments") List<PoloniexAdjustment> adjustments,
      @JsonProperty("deposits") List<PoloniexDeposit> deposits,
      @JsonProperty("withdrawals") List<PoloniexWithdrawal> withdrawals) {
    if (error != null) {
      throw new ExchangeException(error);
    }

    this.adjustments = adjustments;
    this.deposits = deposits;
    this.withdrawals = withdrawals;
  }

  public List<PoloniexAdjustment> getAdjustments() {
    return adjustments;
  }

  public List<PoloniexDeposit> getDeposits() {
    return deposits;
  }

  public List<PoloniexWithdrawal> getWithdrawals() {
    return withdrawals;
  }

  @Override
  public String toString() {
    return "PoloniexDepositsWithdrawalsResponse{"
        + "adjustments="
        + adjustments
        + ", deposits="
        + deposits
        + ", withdrawals="
        + withdrawals
        + '}';
  }
}

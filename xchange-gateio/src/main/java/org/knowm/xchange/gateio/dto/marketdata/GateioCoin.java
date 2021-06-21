package org.knowm.xchange.gateio.dto.marketdata;

public class GateioCoin {

  boolean delisted;
  boolean withdrawDisabled;
  boolean withdrawDelayed;
  boolean depositDisabled;
  boolean tradeDisabled;

  public GateioCoin(
      boolean delisted,
      boolean withdrawDisabled,
      boolean withdrawDelayed,
      boolean depositDisabled,
      boolean tradeDisabled) {
    this.delisted = delisted;
    this.withdrawDisabled = withdrawDisabled;
    this.withdrawDelayed = withdrawDelayed;
    this.depositDisabled = depositDisabled;
    this.tradeDisabled = tradeDisabled;
  }

  @Override
  public String toString() {
    return "GateioCoin{"
        + "delisted="
        + delisted
        + ", withdrawDisabled="
        + withdrawDisabled
        + ", withdrawDelayed="
        + withdrawDelayed
        + ", depositDisabled="
        + depositDisabled
        + ", tradeDisabled="
        + tradeDisabled
        + '}';
  }

  public boolean isDelisted() {
    return delisted;
  }

  public boolean isWithdrawDisabled() {
    return withdrawDisabled;
  }

  public boolean isWithdrawDelayed() {
    return withdrawDelayed;
  }

  public boolean isDepositDisabled() {
    return depositDisabled;
  }

  public boolean isTradeDisabled() {
    return tradeDisabled;
  }
}

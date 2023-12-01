package org.knowm.xchange.btcturk.dto;

import org.knowm.xchange.dto.account.FundingRecord.Type;

/**
 * @author mertguner
 */
public enum BTCTurkOperations {
  sell("sell"),
  trade("trade"),
  withdrawal("withdrawal"),
  buy("buy"),
  deposit("deposit"),
  commission("commission");

  private final String Operation;

  BTCTurkOperations(String Operation) {
    this.Operation = Operation;
  }

  public Type getType() {
    switch (Operation) {
      case "sell":
      case "trade":
      case "withdrawal":
        return Type.WITHDRAWAL;
      case "buy":
      case "deposit":
      case "commission":
        return Type.DEPOSIT;
      default:
        throw new RuntimeException("Unknown BTCTurk transaction type: " + Operation);
    }
  }

  @Override
  public String toString() {
    return Operation;
  }
}

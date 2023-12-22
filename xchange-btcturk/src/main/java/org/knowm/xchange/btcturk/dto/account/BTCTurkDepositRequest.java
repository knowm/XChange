package org.knowm.xchange.btcturk.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author mertguner
 */
public class BTCTurkDepositRequest {

  private final String amount;
  private final String amountPrecision;

  public BTCTurkDepositRequest(
      @JsonProperty("amount") String amount,
      @JsonProperty("amount_precision") String amountPrecision) {
    this.amount = amount;
    this.amountPrecision = amountPrecision;
  }

  public String getAmount() {
    return amount;
  }

  public String getAmountPrecision() {
    return amountPrecision;
  }

  @Override
  public String toString() {
    return "BTCTurkDepositRequest [amount=" + amount + ", amountPrecision=" + amountPrecision + "]";
  }
}

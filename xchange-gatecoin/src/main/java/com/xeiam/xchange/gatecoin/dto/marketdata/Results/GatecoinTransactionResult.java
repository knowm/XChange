package com.xeiam.xchange.gatecoin.dto.marketdata.Results;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.gatecoin.dto.GatecoinResult;
import com.xeiam.xchange.gatecoin.dto.marketdata.GatecoinTransaction;
import com.xeiam.xchange.gatecoin.dto.marketdata.ResponseStatus;

/**
 * @author sumdeha
 */
public class GatecoinTransactionResult extends GatecoinResult {

  private final GatecoinTransaction[] transactions;

  @JsonCreator
  public GatecoinTransactionResult(
      @JsonProperty("transactions") GatecoinTransaction[] transactions,
      @JsonProperty("responseStatus") ResponseStatus responseStatus
  ) {
    super(responseStatus);
    this.transactions = transactions;
  }

  public GatecoinTransaction[] getTransactions() {
    return transactions;
  }
}

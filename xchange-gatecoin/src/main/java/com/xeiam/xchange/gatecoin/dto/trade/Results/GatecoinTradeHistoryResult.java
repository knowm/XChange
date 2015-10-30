package com.xeiam.xchange.gatecoin.dto.trade.Results;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.gatecoin.dto.GatecoinResult;
import com.xeiam.xchange.gatecoin.dto.marketdata.ResponseStatus;
import com.xeiam.xchange.gatecoin.dto.trade.GatecoinTradeHistory;

/**
 * @author sumedha
 */
public class GatecoinTradeHistoryResult extends GatecoinResult {
  private final GatecoinTradeHistory[] transactions;

  public GatecoinTradeHistoryResult(
      @JsonProperty("transactions") GatecoinTradeHistory[] transactions,
      @JsonProperty("responseStatus") ResponseStatus responseStatus
  ) {
    super(responseStatus);
    this.transactions = transactions;
  }

  public GatecoinTradeHistory[] getTransactions() {
    return transactions;
  }
}

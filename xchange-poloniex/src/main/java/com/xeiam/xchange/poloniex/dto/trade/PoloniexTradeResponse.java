package com.xeiam.xchange.poloniex.dto.trade;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.poloniex.dto.marketdata.PoloniexPublicTrade;

import si.mazi.rescu.ExceptionalReturnContentException;

public class PoloniexTradeResponse {

  private final Integer orderNumber;
  private final List<PoloniexPublicTrade> resultingTrades = new ArrayList<PoloniexPublicTrade>();

  @JsonCreator
  public PoloniexTradeResponse(@JsonProperty("orderNumber") Integer orderNumber,
      @JsonProperty("resultingTrades") List<PoloniexPublicTrade> resultingTrades) {

    if (orderNumber == null) {
      throw new ExceptionalReturnContentException("No trade data in response");
    }
    this.orderNumber = orderNumber;
    if (resultingTrades != null) {
      this.resultingTrades.addAll(resultingTrades);
    }
  }

  public Integer getOrderNumber() {

    return orderNumber;
  }

  public List<PoloniexPublicTrade> getPoloniexPublicTrades() {

    return resultingTrades;
  }

}

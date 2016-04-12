package org.knowm.xchange.poloniex.dto.trade;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.poloniex.dto.marketdata.PoloniexPublicTrade;

import si.mazi.rescu.ExceptionalReturnContentException;

public class PoloniexTradeResponse {

  private final Long orderNumber;
  private final List<PoloniexPublicTrade> resultingTrades = new ArrayList<PoloniexPublicTrade>();

  @JsonCreator
  public PoloniexTradeResponse(@JsonProperty("orderNumber") Long orderNumber,
      @JsonProperty("resultingTrades") List<PoloniexPublicTrade> resultingTrades) {

    if (orderNumber == null) {
      throw new ExceptionalReturnContentException("No trade data in response");
    }
    this.orderNumber = orderNumber;
    if (resultingTrades != null) {
      this.resultingTrades.addAll(resultingTrades);
    }
  }

  public Long getOrderNumber() {

    return orderNumber;
  }

  public List<PoloniexPublicTrade> getPoloniexPublicTrades() {

    return resultingTrades;
  }

}

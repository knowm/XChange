package org.knowm.xchange.poloniex.dto.trade;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.List;
import org.knowm.xchange.poloniex.dto.marketdata.PoloniexPublicTrade;
import si.mazi.rescu.ExceptionalReturnContentException;

public class PoloniexTradeResponse {

  private final Long orderNumber;
  private final List<PoloniexPublicTrade> resultingTrades;

  /** Returned on FOK and IOC orders to indicate how much has been executed. */
  private final BigDecimal amountUnfilled;

  @JsonCreator
  public PoloniexTradeResponse(
      @JsonProperty("orderNumber") Long orderNumber,
      @JsonProperty("resultingTrades") List<PoloniexPublicTrade> resultingTrades,
      @JsonProperty("amountUnfilled") BigDecimal amountUnfilled) {

    if (orderNumber == null) {
      throw new ExceptionalReturnContentException("No trade data in response");
    }
    this.orderNumber = orderNumber;
    this.resultingTrades = resultingTrades;
    this.amountUnfilled = amountUnfilled;
  }

  public Long getOrderNumber() {
    return orderNumber;
  }

  public List<PoloniexPublicTrade> getPoloniexPublicTrades() {
    return resultingTrades;
  }

  public BigDecimal getAmountUnfilled() {
    return amountUnfilled;
  }
}

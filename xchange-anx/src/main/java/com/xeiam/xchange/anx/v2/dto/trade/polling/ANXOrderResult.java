package com.xeiam.xchange.anx.v2.dto.trade.polling;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.anx.v2.dto.ANXValue;

/**
 * Data object representing Open Orders from ANX
 */
public final class ANXOrderResult {

  private final ANXValue avgCost;
  private final String orderId;
  private final ANXValue totalAmount;
  private final ANXValue totalSpent;
  private final ANXOrderResultTrade[] trades;

  /**
   * Constructor
   * 
   * @param avgCost
   * @param orderId
   * @param totalAmount
   * @param totalSpent
   * @param trades
   */
  public ANXOrderResult(@JsonProperty("avg_cost") ANXValue avgCost, @JsonProperty("order_id") String orderId, @JsonProperty("total_amount") ANXValue totalAmount,
      @JsonProperty("total_spent") ANXValue totalSpent, @JsonProperty("trades") ANXOrderResultTrade[] trades) {

    this.avgCost = avgCost;
    this.orderId = orderId;
    this.totalAmount = totalAmount;
    this.totalSpent = totalSpent;
    this.trades = trades;
  }

  public ANXValue getAvgCost() {

    return avgCost;
  }

  public String getOrderId() {

    return orderId;
  }

  public ANXValue getTotalAmount() {

    return totalAmount;
  }

  public ANXValue getTotalSpent() {

    return totalSpent;
  }

  public ANXOrderResultTrade[] getTrades() {

    return trades;
  }

  @Override
  public String toString() {

    String tradesString = "[";
    for (int i = 0; i < trades.length; i++)
      tradesString += ((i > 0) ? ", " : "") + trades[i].toString();
    tradesString += "]";
    return "ANXOpenOrder [avgCost=" + avgCost + ", orderId=" + orderId + ", totalAmount=" + totalAmount + ", totalSpent=" + totalSpent + ", trades=" + tradesString + "]";
  }

}

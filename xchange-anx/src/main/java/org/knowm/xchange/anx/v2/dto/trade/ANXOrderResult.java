package org.knowm.xchange.anx.v2.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.anx.v2.dto.ANXValue;

/** Data object representing Open Orders from ANX */
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
  public ANXOrderResult(
      @JsonProperty("avg_cost") ANXValue avgCost,
      @JsonProperty("order_id") String orderId,
      @JsonProperty("total_amount") ANXValue totalAmount,
      @JsonProperty("total_spent") ANXValue totalSpent,
      @JsonProperty("trades") ANXOrderResultTrade[] trades) {

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

    StringBuilder tradesString = new StringBuilder("[");
    for (int i = 0; i < trades.length; i++)
      tradesString.append((i > 0) ? ", " : "").append(trades[i].toString());
    tradesString.append("]");
    return "ANXOpenOrder [avgCost="
        + avgCost
        + ", orderId="
        + orderId
        + ", totalAmount="
        + totalAmount
        + ", totalSpent="
        + totalSpent
        + ", trades="
        + tradesString
        + "]";
  }
}

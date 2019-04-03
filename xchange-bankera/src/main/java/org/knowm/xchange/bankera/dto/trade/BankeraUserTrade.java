package org.knowm.xchange.bankera.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BankeraUserTrade {

  private final long id;
  private final long orderId;
  private final String market;
  private final String amount;
  private final String price;
  private final String feeAmount;
  private final String total;
  private final String status;
  private final String side;
  private final String completedAt;

  public BankeraUserTrade(
      @JsonProperty("id") long id,
      @JsonProperty("order_id") long orderId,
      @JsonProperty("market") String market,
      @JsonProperty("price") String price,
      @JsonProperty("amount") String amount,
      @JsonProperty("fee_amount") String feeAmount,
      @JsonProperty("total") String total,
      @JsonProperty("status") String status,
      @JsonProperty("side") String side,
      @JsonProperty("completed_at") String completedAt) {
    this.id = id;
    this.orderId = orderId;
    this.market = market;
    this.amount = amount;
    this.feeAmount = feeAmount;
    this.total = total;
    this.status = status;
    this.side = side;
    this.completedAt = completedAt;
    this.price = price;
  }

  public long getId() {
    return id;
  }

  public long getOrderId() {
    return orderId;
  }

  public String getMarket() {
    return market;
  }

  public String getAmount() {
    return amount;
  }

  public String getFeeAmount() {
    return feeAmount;
  }

  public String getTotal() {
    return total;
  }

  public String getStatus() {
    return status;
  }

  public String getSide() {
    return side;
  }

  public String getCompletedAt() {
    return completedAt;
  }

  public String getPrice() {
    return price;
  }
}

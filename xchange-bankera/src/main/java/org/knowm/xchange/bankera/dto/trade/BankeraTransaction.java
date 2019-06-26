package org.knowm.xchange.bankera.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BankeraTransaction {

  private final long id;
  private final long orderId;
  private final String market;
  private final String amount;
  private final String fee;
  private final String price;
  private final String total;
  private final String status;
  private final String type;
  private final String platform;
  private final String completedAt;

  public BankeraTransaction(
      @JsonProperty("id") long id,
      @JsonProperty("orderId") long orderId,
      @JsonProperty("market") String market,
      @JsonProperty("amount") String amount,
      @JsonProperty("fee") String fee,
      @JsonProperty("price") String price,
      @JsonProperty("total") String total,
      @JsonProperty("status") String status,
      @JsonProperty("type") String type,
      @JsonProperty("platform") String platform,
      @JsonProperty("completed_at") String completedAt) {
    this.id = id;
    this.orderId = orderId;
    this.market = market;
    this.amount = amount;
    this.fee = fee;
    this.price = price;
    this.total = total;
    this.status = status;
    this.type = type;
    this.platform = platform;
    this.completedAt = completedAt;
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

  public String getFee() {
    return fee;
  }

  public String getPrice() {
    return price;
  }

  public String getTotal() {
    return total;
  }

  public String getStatus() {
    return status;
  }

  public String getType() {
    return type;
  }

  public String getPlatform() {
    return platform;
  }

  public String getCompletedAt() {
    return completedAt;
  }
}

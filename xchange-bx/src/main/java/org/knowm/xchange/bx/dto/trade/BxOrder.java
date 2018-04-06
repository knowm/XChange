package org.knowm.xchange.bx.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class BxOrder {

  private final String pairingId;
  private final String orderId;
  private final String orderType;
  private final BigDecimal amount;
  private final BigDecimal rate;
  private final String date;

  public BxOrder(
      @JsonProperty("pairing_id") String pairingId,
      @JsonProperty("order_id") String orderId,
      @JsonProperty("order_type") String orderType,
      @JsonProperty("amount") BigDecimal amount,
      @JsonProperty("rate") BigDecimal rate,
      @JsonProperty("date") String date) {
    this.pairingId = pairingId;
    this.orderId = orderId;
    this.orderType = orderType;
    this.amount = amount;
    this.rate = rate;
    this.date = date;
  }

  public String getPairingId() {
    return pairingId;
  }

  public String getOrderId() {
    return orderId;
  }

  public String getOrderType() {
    return orderType;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public BigDecimal getRate() {
    return rate;
  }

  public String getDate() {
    return date;
  }

  @Override
  public String toString() {
    return "BxOrder{"
        + "pairingId='"
        + pairingId
        + '\''
        + ", orderId='"
        + orderId
        + '\''
        + ", orderType='"
        + orderType
        + '\''
        + ", amount="
        + amount
        + ", rate="
        + rate
        + ", date="
        + date
        + '}';
  }
}

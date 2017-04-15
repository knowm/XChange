package org.knowm.xchange.huobi.dto.trade;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

public class HuobiOrderInfo {

  public enum HuobiOrderStatus {
    NOT_COMPLETED(0), PARTIALLY_COMPLETED(1), COMPLETED(2), CANCELLED(3), ABANDONED(4), ABNORMAL(5), PARTIALLY_CANCELLED(6), QUEUE(7);

    private final int statusCode;

    HuobiOrderStatus(final int code) {
      this.statusCode = code;
    }

    @JsonValue
    public int toInt() {
      return this.statusCode;
    }
  }

  private final long id;
  private final int type;
  private final BigDecimal orderPrice;
  private final BigDecimal orderAmount;
  private final BigDecimal processedAmount;
  private final BigDecimal processedPrice;
  private final BigDecimal vot;
  private final BigDecimal fee;
  private final BigDecimal total;
  private final HuobiOrderStatus status;

  public HuobiOrderInfo(@JsonProperty("id") final long id, @JsonProperty("type") final int type,
      @JsonProperty("order_price") final BigDecimal orderPrice, @JsonProperty("order_amount") final BigDecimal orderAmount,
      @JsonProperty("processed_price") final BigDecimal processedPrice, @JsonProperty("processed_amount") final BigDecimal processedAmount,
      @JsonProperty("vot") final BigDecimal vot, @JsonProperty("fee") final BigDecimal fee, @JsonProperty("total") final BigDecimal total,
      @JsonProperty("status") final HuobiOrderStatus status) {

    this.id = id;
    this.type = type;
    this.orderPrice = orderPrice;
    this.orderAmount = orderAmount;
    this.processedAmount = processedAmount;
    this.processedPrice = processedPrice;
    this.vot = vot;
    this.fee = fee;
    this.total = total;
    this.status = status;
  }

  public long getId() {
    return id;
  }

  public int getType() {
    return type;
  }

  public BigDecimal getOrderPrice() {
    return orderPrice;
  }

  public BigDecimal getOrderAmount() {
    return orderAmount;
  }

  public BigDecimal getProcessedAmount() {
    return processedAmount;
  }

  public BigDecimal getProcessedPrice() {
    return processedPrice;
  }

  public BigDecimal getVot() {
    return vot;
  }

  public BigDecimal getFee() {
    return fee;
  }

  public BigDecimal getTotal() {
    return total;
  }

  public HuobiOrderStatus getStatus() {
    return status;
  }

}

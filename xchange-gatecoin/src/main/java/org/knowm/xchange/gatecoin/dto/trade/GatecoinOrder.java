package org.knowm.xchange.gatecoin.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

/** @author Sumedha */
public final class GatecoinOrder {

  private final String code;
  private final String clOrderId;
  private final int side;
  private final BigDecimal price;
  private final BigDecimal initialQuantity;
  private final BigDecimal remainingQuantity;
  private final int status;
  private final String statusDesc;
  private final String tranSeqNo;
  private final int type;
  private final String date;

  public GatecoinOrder(
      @JsonProperty("code") String code,
      @JsonProperty("clOrderId") String clOrderId,
      @JsonProperty("side") int side,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("initialQuantity") BigDecimal initialQuantity,
      @JsonProperty("remainingQuantity") BigDecimal remainingQuantity,
      @JsonProperty("status") int status,
      @JsonProperty("statusDesc") String statusDesc,
      @JsonProperty("tranSeqNo") String tranSeqNo,
      @JsonProperty("type") int type,
      @JsonProperty("date") String date) {
    this.code = code;
    this.clOrderId = clOrderId;
    this.side = side;
    this.price = price;
    this.initialQuantity = initialQuantity;
    this.remainingQuantity = remainingQuantity;
    this.status = status;
    this.statusDesc = statusDesc;
    this.tranSeqNo = tranSeqNo;
    this.type = type;
    this.date = date;
  }

  public String getCode() {
    return code;
  }

  public String getClOrderId() {
    return clOrderId;
  }

  public int getSide() {
    return side;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public BigDecimal getInitialQuantity() {
    return initialQuantity;
  }

  public BigDecimal getRemainingQuantity() {
    return remainingQuantity;
  }

  public int getStatus() {
    return status;
  }

  public String getStatusDesc() {
    return statusDesc;
  }

  public String getTransSeqNo() {
    return tranSeqNo;
  }

  public int getType() {
    return type;
  }

  public String getDate() {
    return date;
  }

  @Override
  public String toString() {

    return String.format(
        "Order{id=%s, datetime=%s, type=%s, price=%s, initialQuantity=%s,remainingQuantity=%s}",
        clOrderId, date, type, price, initialQuantity, remainingQuantity);
  }
}

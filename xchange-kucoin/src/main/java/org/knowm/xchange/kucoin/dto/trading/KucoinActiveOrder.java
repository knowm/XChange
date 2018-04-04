package org.knowm.xchange.kucoin.dto.trading;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.math.BigDecimal;
import java.sql.Date;
import org.knowm.xchange.kucoin.dto.KucoinOrderType;

@JsonDeserialize(using = KucoinActiveOrderDeserializer.class)
public class KucoinActiveOrder {

  // arr[0] Time arr[1] Order Type arr[2] Price arr[3] Amount arr[4] Deal Amount arr[5] OrderOid
  private Date timestamp;
  private KucoinOrderType orderType;
  private BigDecimal price;
  private BigDecimal amount;
  private BigDecimal dealAmount;
  private String orderOid;

  public KucoinActiveOrder(
      Date timestamp,
      KucoinOrderType orderType,
      BigDecimal price,
      BigDecimal amount,
      BigDecimal dealAmount,
      String orderOid) {
    super();
    this.timestamp = timestamp;
    this.orderType = orderType;
    this.price = price;
    this.amount = amount;
    this.dealAmount = dealAmount;
    this.orderOid = orderOid;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
  }

  public KucoinOrderType getOrderType() {
    return orderType;
  }

  public void setOrderType(KucoinOrderType orderType) {
    this.orderType = orderType;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public BigDecimal getDealAmount() {
    return dealAmount;
  }

  public void setDealAmount(BigDecimal dealAmount) {
    this.dealAmount = dealAmount;
  }

  public String getOrderOid() {
    return orderOid;
  }

  public void setOrderOid(String orderOid) {
    this.orderOid = orderOid;
  }
}

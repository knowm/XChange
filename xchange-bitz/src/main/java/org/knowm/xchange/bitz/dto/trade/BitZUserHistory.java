package org.knowm.xchange.bitz.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;

public class BitZUserHistory {
  private final long id;
  private final long uid;
  private final BigDecimal price;
  private final BigDecimal number;
  private final BigDecimal total;
  private final BigDecimal numberOver;
  private final BigDecimal numberDeal;
  private final String flag;
  private final Integer status;
  private final String isNew;
  private final String coinFrom;
  private final String coinTo;
  private final BigDecimal orderTotalPrice;
  private final Date created;

  public BitZUserHistory(
      @JsonProperty("id") long id,
      @JsonProperty("uid") long uid,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("number") BigDecimal number,
      @JsonProperty("total") BigDecimal total,
      @JsonProperty("numberOver") BigDecimal numberOver,
      @JsonProperty("numberDeal") BigDecimal numberDeal,
      @JsonProperty("flag") String flag,
      @JsonProperty("status") Integer status,
      @JsonProperty("isNew") String isNew,
      @JsonProperty("coinFrom") String coinFrom,
      @JsonProperty("coinTo") String coinTo,
      @JsonProperty("orderTotalPrice") BigDecimal orderTotalPrice,
      @JsonProperty("created") long created) {
    this.id = id;
    this.uid = uid;
    this.price = price;
    this.number = number;
    this.total = total;
    this.numberOver = numberOver;
    this.numberDeal = numberDeal;
    this.flag = flag;
    this.status = status;
    this.isNew = isNew;
    this.coinFrom = coinFrom;
    this.coinTo = coinTo;
    this.orderTotalPrice = orderTotalPrice;
    this.created = new Date(created * 1000);
  }

  public long getId() {
    return id;
  }

  public long getUid() {
    return uid;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public BigDecimal getNumber() {
    return number;
  }

  public BigDecimal getTotal() {
    return total;
  }

  public BigDecimal getNumberOver() {
    return numberOver;
  }

  public BigDecimal getNumberDeal() {
    return numberDeal;
  }

  public String getFlag() {
    return flag;
  }

  public Integer getStatus() {
    return status;
  }

  public String getIsNew() {
    return isNew;
  }

  public String getCoinFrom() {
    return coinFrom;
  }

  public String getCoinTo() {
    return coinTo;
  }

  public Date getCreated() {
    return created;
  }

  public BigDecimal getOrderTotalPrice() {
    return orderTotalPrice;
  }

  @Override
  public String toString() {
    return "BitZUserHistory{"
        + "id="
        + id
        + ", uid="
        + uid
        + ", price="
        + price
        + ", number="
        + number
        + ", total="
        + total
        + ", numberOver="
        + numberOver
        + ", numberDeal="
        + numberDeal
        + ", flag='"
        + flag
        + '\''
        + ", status="
        + status
        + ", isNew='"
        + isNew
        + '\''
        + ", coinFrom='"
        + coinFrom
        + '\''
        + ", coinTo='"
        + coinTo
        + '\''
        + ", orderTotalPrice="
        + orderTotalPrice
        + ", created="
        + created
        + '}';
  }
}

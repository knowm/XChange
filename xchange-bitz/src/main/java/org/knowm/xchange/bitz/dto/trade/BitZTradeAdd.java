package org.knowm.xchange.bitz.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

// TODO: Implement Once Implemented By The Exchange
public class BitZTradeAdd {
  private final long id;
  private final long uId;
  private final BigDecimal price;
  private final BigDecimal number;
  private final BigDecimal numberOver;
  private final String flag;
  private final String status;
  private final String coinFrom;
  private final String coinTo;
  private final BigDecimal numberDeal;

  public BitZTradeAdd(
      @JsonProperty("id") long id,
      @JsonProperty("uId") long uId,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("number") BigDecimal number,
      @JsonProperty("numberOver") BigDecimal numberOver,
      @JsonProperty("flag") String flag,
      @JsonProperty("status") String status,
      @JsonProperty("coinFrom") String coinFrom,
      @JsonProperty("coinTo") String coinTo,
      @JsonProperty("numberDeal") BigDecimal numberDeal) {
    this.id = id;
    this.uId = uId;
    this.price = price;
    this.number = number;
    this.numberOver = numberOver;
    this.flag = flag;
    this.status = status;
    this.coinFrom = coinFrom;
    this.coinTo = coinTo;
    this.numberDeal = numberDeal;
  }

  public long getId() {
    return id;
  }

  public long getuId() {
    return uId;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public BigDecimal getNumber() {
    return number;
  }

  public String getFlag() {
    return flag;
  }

  public String getStatus() {
    return status;
  }

  public String getCoinFrom() {
    return coinFrom;
  }

  public String getCoinTo() {
    return coinTo;
  }

  public BigDecimal getNumberDeal() {
    return numberDeal;
  }

  public BigDecimal getNumberOver() {
    return numberOver;
  }

  @Override
  public String toString() {
    return "BitZTradeAdd{"
        + "id="
        + id
        + ", uId="
        + uId
        + ", price="
        + price
        + ", number="
        + number
        + ", numberOver="
        + numberOver
        + ", flag='"
        + flag
        + '\''
        + ", status='"
        + status
        + '\''
        + ", coinFrom='"
        + coinFrom
        + '\''
        + ", coinTo='"
        + coinTo
        + '\''
        + ", numberDeal="
        + numberDeal
        + '}';
  }
}

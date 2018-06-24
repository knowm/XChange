package org.knowm.xchange.bittrex.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class BittrexTrade {

  private String fillType;
  private String id;
  private String orderType;
  private BigDecimal price;
  private BigDecimal quantity;
  private String timeStamp;
  private BigDecimal total;

  public BittrexTrade(
      @JsonProperty("FillType") String fillType,
      @JsonProperty("Id") String id,
      @JsonProperty("OrderType") String orderType,
      @JsonProperty("Price") BigDecimal price,
      @JsonProperty("Quantity") BigDecimal quantity,
      @JsonProperty("TimeStamp") String timeStamp,
      @JsonProperty("Total") BigDecimal total) {

    super();
    this.fillType = fillType;
    this.id = id;
    this.orderType = orderType;
    this.price = price;
    this.quantity = quantity;
    this.timeStamp = timeStamp;
    this.total = total;
  }

  public String getFillType() {

    return this.fillType;
  }

  public void setFillType(String fillType) {

    this.fillType = fillType;
  }

  public String getId() {

    return this.id;
  }

  public void setId(String id) {

    this.id = id;
  }

  public String getOrderType() {

    return this.orderType;
  }

  public void setOrderType(String orderType) {

    this.orderType = orderType;
  }

  public BigDecimal getPrice() {

    return this.price;
  }

  public void setPrice(BigDecimal price) {

    this.price = price;
  }

  public BigDecimal getQuantity() {

    return this.quantity;
  }

  public void setQuantity(BigDecimal quantity) {

    this.quantity = quantity;
  }

  public String getTimeStamp() {

    return this.timeStamp;
  }

  public void setTimeStamp(String timeStamp) {

    this.timeStamp = timeStamp;
  }

  public BigDecimal getTotal() {

    return this.total;
  }

  public void setTotal(BigDecimal total) {

    this.total = total;
  }

  @Override
  public String toString() {

    return "BittrexTrade [fillType="
        + fillType
        + ", id="
        + id
        + ", orderType="
        + orderType
        + ", price="
        + price
        + ", quantity="
        + quantity
        + ", timeStamp="
        + timeStamp
        + ", total="
        + total
        + "]";
  }
}

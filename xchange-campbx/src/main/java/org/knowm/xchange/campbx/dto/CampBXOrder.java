package org.knowm.xchange.campbx.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.math.BigDecimal;
import java.util.Date;
import si.mazi.rescu.serialization.jackson.serializers.SqlTimeDeserializer;
import si.mazi.rescu.serialization.jackson.serializers.YesNoBooleanDeserializerImpl;

/** @author Matija Mazi */
public class CampBXOrder extends CampBXResponse {

  @JsonProperty("Order Entered")
  @JsonDeserialize(using = SqlTimeDeserializer.class)
  private Date orderEntered;

  @JsonProperty("Order Expiry")
  @JsonDeserialize(using = SqlTimeDeserializer.class)
  private Date orderExpiry;

  @JsonProperty("Order Type")
  private String orderType;

  @JsonProperty("Margin Percent")
  private String marginPercent;

  @JsonProperty("Quantity")
  private BigDecimal quantity;

  @JsonProperty("Price")
  private BigDecimal price;

  @JsonProperty("Stop-loss")
  @JsonDeserialize(using = YesNoBooleanDeserializerImpl.class)
  private Boolean stopLoss;

  @JsonProperty("Fill Type")
  private String fillType;

  @JsonProperty("Dark Pool")
  @JsonDeserialize(using = YesNoBooleanDeserializerImpl.class)
  private Boolean darkPool;

  @JsonProperty("Order ID")
  private String orderID;

  @JsonProperty("Order Entered")
  public Date getOrderEntered() {

    return orderEntered;
  }

  @JsonProperty("Order Entered")
  public void setOrderEntered(Date orderEntered) {

    this.orderEntered = orderEntered;
  }

  @JsonProperty("Order Expiry")
  public Date getOrderExpiry() {

    return orderExpiry;
  }

  @JsonProperty("Order Expiry")
  public void setOrderExpiry(Date orderExpiry) {

    this.orderExpiry = orderExpiry;
  }

  @JsonProperty("Order Type")
  public String getOrderType() {

    return orderType;
  }

  @JsonProperty("Order Type")
  public void setOrderType(String orderType) {

    this.orderType = orderType;
  }

  @JsonProperty("Margin Percent")
  public String getMarginPercent() {

    return marginPercent;
  }

  @JsonProperty("Margin Percent")
  public void setMarginPercent(String marginPercent) {

    this.marginPercent = marginPercent;
  }

  @JsonProperty("Quantity")
  public BigDecimal getQuantity() {

    return quantity;
  }

  @JsonProperty("Quantity")
  public void setQuantity(BigDecimal quantity) {

    this.quantity = quantity;
  }

  @JsonProperty("Price")
  public BigDecimal getPrice() {

    return price;
  }

  @JsonProperty("Price")
  public void setPrice(BigDecimal price) {

    this.price = price;
  }

  @JsonProperty("Stop-loss")
  public Boolean getStopLoss() {

    return stopLoss;
  }

  @JsonProperty("Stop-loss")
  public void setStopLoss(Boolean stopLoss) {

    this.stopLoss = stopLoss;
  }

  @JsonProperty("Fill Type")
  public String getFillType() {

    return fillType;
  }

  @JsonProperty("Fill Type")
  public void setFillType(String fillType) {

    this.fillType = fillType;
  }

  @JsonProperty("Dark Pool")
  public Boolean getDarkPool() {

    return darkPool;
  }

  @JsonProperty("Dark Pool")
  public void setDarkPool(Boolean darkPool) {

    this.darkPool = darkPool;
  }

  @JsonProperty("Order ID")
  public String getOrderID() {

    return orderID;
  }

  @JsonProperty("Order ID")
  public void setOrderID(String orderID) {

    this.orderID = orderID;
  }

  @Override
  public String toString() {

    return String.format(
        "CampBXOrder{orderEntered=%s, orderExpiry=%s, orderType='%s', marginPercent='%s', quantity=%s, price=%s, stopLoss='%s', fillType='%s', darkPool='%s', orderID='%s'}",
        orderEntered,
        orderExpiry,
        orderType,
        marginPercent,
        quantity,
        price,
        stopLoss,
        fillType,
        darkPool,
        orderID);
  }
}

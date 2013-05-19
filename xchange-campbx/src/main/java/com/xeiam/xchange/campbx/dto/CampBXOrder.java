/*
 * Copyright (C) 2013 Matija Mazi
 * Copyright (C) 2013 Xeiam LLC http://xeiam.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.xeiam.xchange.campbx.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.xeiam.xchange.utils.jackson.SqlTimeDeserializer;
import com.xeiam.xchange.utils.jackson.YesNoBooleanDeserializerImpl;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Matija Mazi <br/>
 */
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

    return String.format("CampBXOrder{orderEntered=%s, orderExpiry=%s, orderType='%s', marginPercent='%s', quantity=%s, price=%s, stopLoss='%s', fillType='%s', darkPool='%s', orderID='%s'}",
        orderEntered, orderExpiry, orderType, marginPercent, quantity, price, stopLoss, fillType, darkPool, orderID);
  }
}
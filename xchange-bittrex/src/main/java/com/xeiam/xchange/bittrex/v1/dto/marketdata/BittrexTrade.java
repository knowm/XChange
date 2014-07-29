/**
 * The MIT License
 * Copyright (c) 2012 Xeiam LLC http://xeiam.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.xeiam.xchange.bittrex.v1.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BittrexTrade {

  private String fillType;
  private String id;
  private String orderType;
  private BigDecimal price;
  private BigDecimal quantity;
  private String timeStamp;
  private BigDecimal total;

  public BittrexTrade(@JsonProperty("FillType") String fillType, @JsonProperty("Id") String id, @JsonProperty("OrderType") String orderType, @JsonProperty("Price") BigDecimal price,
      @JsonProperty("Quantity") BigDecimal quantity, @JsonProperty("TimeStamp") String timeStamp, @JsonProperty("Total") BigDecimal total) {

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

    return "BittrexTrade [fillType=" + fillType + ", id=" + id + ", orderType=" + orderType + ", price=" + price + ", quantity=" + quantity + ", timeStamp=" + timeStamp + ", total=" + total + "]";
  }

}
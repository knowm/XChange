/**
 * Copyright (C) 2012 - 2014 Xeiam LLC http://xeiam.com
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
package com.xeiam.xchange.kraken.dto.trade;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class KrakenOrderDescription {

  private final String assetPair;
  private final String type;
  private final String orderType;
  private final BigDecimal price;
  private final BigDecimal secondaryPrice;
  private final String leverage;
  private final String positionTxId;
  private final String orderDescription;
  private final String closeDescription;

  public KrakenOrderDescription(@JsonProperty("pair") String assetPair, @JsonProperty("type") String type, @JsonProperty("ordertype") String orderType, @JsonProperty("price") BigDecimal price,
      @JsonProperty("price2") BigDecimal secondaryPrice, @JsonProperty("leverage") String leverage, @JsonProperty("position") String positionTxId, @JsonProperty("order") String orderDescription,
      @JsonProperty("close") String closeDescription) {

    this.assetPair = assetPair;
    this.type = type;
    this.orderType = orderType;
    this.price = price;
    this.secondaryPrice = secondaryPrice;
    this.leverage = leverage;
    this.positionTxId = positionTxId;
    this.orderDescription = orderDescription;
    this.closeDescription = closeDescription;
  }

  public String getAssetPair() {

    return assetPair;
  }

  public String getType() {

    return type;
  }

  public String getOrderType() {

    return orderType;
  }

  public BigDecimal getPrice() {

    return price;
  }

  public BigDecimal getSecondaryPrice() {

    return secondaryPrice;
  }

  public String getLeverage() {

    return leverage;
  }

  public String getPositionTxId() {

    return positionTxId;
  }

  public String getOrderDescription() {

    return orderDescription;
  }

  public String getCloseDescription() {

    return closeDescription;
  }

  @Override
  public String toString() {

    return "KrakenOrderDescription [assetPair=" + assetPair + ", type=" + type + ", orderType=" + orderType + ", price=" + price + ", secondaryPrice=" + secondaryPrice + ", leverage=" + leverage
        + ", positionTxId=" + positionTxId + ", orderDescription=" + orderDescription + ", closeDescription=" + closeDescription + "]";
  }

}
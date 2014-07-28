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
package com.xeiam.xchange.kraken.dto.trade;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class KrakenTrade {

  private final String orderTxId;
  private final String assetPair;
  private final double unixTimestamp;
  private final KrakenType type;
  private final KrakenOrderType orderType;
  private final BigDecimal price;
  private final BigDecimal cost;
  private final BigDecimal fee;
  private final BigDecimal volume;
  private final BigDecimal margin;
  private final String miscellaneous;
  private final String closing;
  private final String positionStatus;
  private final BigDecimal averageClosePrice;
  private final BigDecimal closeCost;
  private final BigDecimal closeFee;
  private final BigDecimal closeVolume;
  private final BigDecimal closeMargin;
  private final BigDecimal netDifference;
  private final List<String> tradeIds;

  /**
   * Constructor
   * 
   * @param orderTxId
   * @param assetPair
   * @param unixTimestamp
   * @param type
   * @param orderType
   * @param price
   * @param cost
   * @param fee
   * @param volume
   * @param margin
   * @param miscellaneous
   * @param closing
   * @param positionStatus
   * @param averageClosePrice
   * @param closeCost
   * @param closeFee
   * @param closeVolume
   * @param closeMargin
   * @param netDifference
   * @param tradeIds
   */
  public KrakenTrade(@JsonProperty("ordertxid") String orderTxId, @JsonProperty("pair") String assetPair, @JsonProperty("time") double unixTimestamp, @JsonProperty("type") KrakenType type,
      @JsonProperty("ordertype") KrakenOrderType orderType, @JsonProperty("price") BigDecimal price, @JsonProperty("cost") BigDecimal cost, @JsonProperty("fee") BigDecimal fee,
      @JsonProperty("vol") BigDecimal volume, @JsonProperty("margin") BigDecimal margin, @JsonProperty("misc") String miscellaneous, @JsonProperty("closing") String closing,
      @JsonProperty("posstatus") String positionStatus, @JsonProperty("cprice") BigDecimal averageClosePrice, @JsonProperty("ccost") BigDecimal closeCost, @JsonProperty("cfee") BigDecimal closeFee,
      @JsonProperty("cvol") BigDecimal closeVolume, @JsonProperty("cmargin") BigDecimal closeMargin, @JsonProperty("net") BigDecimal netDifference, @JsonProperty("trades") List<String> tradeIds) {

    this.orderTxId = orderTxId;
    this.assetPair = assetPair;
    this.unixTimestamp = unixTimestamp;
    this.type = type;
    this.orderType = orderType;
    this.price = price;
    this.cost = cost;
    this.fee = fee;
    this.volume = volume;
    this.margin = margin;
    this.miscellaneous = miscellaneous;
    this.closing = closing;
    this.positionStatus = positionStatus;
    this.averageClosePrice = averageClosePrice;
    this.closeCost = closeCost;
    this.closeFee = closeFee;
    this.closeVolume = closeVolume;
    this.closeMargin = closeMargin;
    this.netDifference = netDifference;
    this.tradeIds = tradeIds;
  }

  public String getOrderTxId() {

    return orderTxId;
  }

  public String getAssetPair() {

    return assetPair;
  }

  public double getUnixTimestamp() {

    return unixTimestamp;
  }

  public KrakenType getType() {

    return type;
  }

  public KrakenOrderType getOrderType() {

    return orderType;
  }

  public BigDecimal getPrice() {

    return price;
  }

  public BigDecimal getCost() {

    return cost;
  }

  public BigDecimal getFee() {

    return fee;
  }

  public BigDecimal getVolume() {

    return volume;
  }

  public BigDecimal getMargin() {

    return margin;
  }

  public String getMiscellaneous() {

    return miscellaneous;
  }

  public String getClosing() {

    return closing;
  }

  public String getPositionStatus() {

    return positionStatus;
  }

  public BigDecimal getAverageClosePrice() {

    return averageClosePrice;
  }

  public BigDecimal getCloseCost() {

    return closeCost;
  }

  public BigDecimal getCloseFee() {

    return closeFee;
  }

  public BigDecimal getCloseVolume() {

    return closeVolume;
  }

  public BigDecimal getCloseMargin() {

    return closeMargin;
  }

  public BigDecimal getNetDifference() {

    return netDifference;
  }

  public List<String> getTradeIds() {

    return tradeIds;
  }

  @Override
  public String toString() {

    return "KrakenTrade [orderTxId=" + orderTxId + ", assetPair=" + assetPair + ", unixTimestamp=" + unixTimestamp + ", type=" + type + ", orderType=" + orderType + ", price=" + price + ", cost="
        + cost + ", fee=" + fee + ", volume=" + volume + ", margin=" + margin + ", miscellaneous=" + miscellaneous + ", closing=" + closing + ", positionStatus=" + positionStatus
        + ", averageClosePrice=" + averageClosePrice + ", closeCost=" + closeCost + ", closeFee=" + closeFee + ", closeVolume=" + closeVolume + ", closeMargin=" + closeMargin + ", netDifference="
        + netDifference + ", tradeIds=" + tradeIds + "]";
  }

}

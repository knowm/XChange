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
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.xeiam.xchange.kraken.dto.trade.KrakenOrderFlags.KrakenOrderFlagsDeserializer;

public class KrakenOpenPosition {

  private final String orderTxId;
  private final String assetPair;
  private final long tradeUnixTimestamp;
  private final KrakenType type;
  private final KrakenOrderType orderType;
  private final BigDecimal cost;
  private final BigDecimal fee;
  private final BigDecimal volume;
  private final BigDecimal volumeClosed;
  private final BigDecimal margin;
  private final BigDecimal value;
  private final BigDecimal netDifference;
  private final String miscellaneous;
  private final Set<KrakenOrderFlags> orderFlags;
  private final BigDecimal volumeInQuoteCurrency;

  /**
   * Constructor
   * 
   * @param orderTxId
   * @param assetPair
   * @param tradeUnixTimestamp
   * @param type
   * @param orderType
   * @param cost
   * @param fee
   * @param volume
   * @param volumeClosed
   * @param margin
   * @param value
   * @param netDifference
   * @param miscellaneous
   * @param orderFlags
   * @param volumeInQuoteCurrency
   */
  public KrakenOpenPosition(@JsonProperty("ordertxid") String orderTxId, @JsonProperty("pair") String assetPair, @JsonProperty("time") long tradeUnixTimestamp, @JsonProperty("type") KrakenType type,
      @JsonProperty("ordertype") KrakenOrderType orderType, @JsonProperty("cost") BigDecimal cost, @JsonProperty("fee") BigDecimal fee, @JsonProperty("vol") BigDecimal volume,
      @JsonProperty("vol_closed") BigDecimal volumeClosed, @JsonProperty("margin") BigDecimal margin, @JsonProperty("volue") BigDecimal value, @JsonProperty("net") BigDecimal netDifference,
      @JsonProperty("misc") String miscellaneous, @JsonProperty("oflags") @JsonDeserialize(using = KrakenOrderFlagsDeserializer.class) Set<KrakenOrderFlags> orderFlags,
      @JsonProperty("viqc") BigDecimal volumeInQuoteCurrency) {

    this.orderTxId = orderTxId;
    this.assetPair = assetPair;
    this.tradeUnixTimestamp = tradeUnixTimestamp;
    this.type = type;
    this.orderType = orderType;
    this.cost = cost;
    this.fee = fee;
    this.volume = volume;
    this.volumeClosed = volumeClosed;
    this.margin = margin;
    this.value = value;
    this.netDifference = netDifference;
    this.miscellaneous = miscellaneous;
    this.orderFlags = orderFlags;
    this.volumeInQuoteCurrency = volumeInQuoteCurrency;
  }

  public String getOrderTxId() {

    return orderTxId;
  }

  public String getAssetPair() {

    return assetPair;
  }

  public long getTradeUnixTimestamp() {

    return tradeUnixTimestamp;
  }

  public KrakenType getType() {

    return type;
  }

  public KrakenOrderType getOrderType() {

    return orderType;
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

  public BigDecimal getVolumeClosed() {

    return volumeClosed;
  }

  public BigDecimal getMargin() {

    return margin;
  }

  public BigDecimal getValue() {

    return value;
  }

  public BigDecimal getNetDifference() {

    return netDifference;
  }

  public String getMiscellaneous() {

    return miscellaneous;
  }

  public Set<KrakenOrderFlags> getOrderFlags() {

    return orderFlags;
  }

  public BigDecimal getVolumeInQuoteCurrency() {

    return volumeInQuoteCurrency;
  }

  @Override
  public String toString() {

    return "KrakenOpenPosition [orderTxId=" + orderTxId + ", assetPair=" + assetPair + ", tradeUnixTimestamp=" + tradeUnixTimestamp + ", type=" + type + ", orderType=" + orderType + ", cost=" + cost
        + ", fee=" + fee + ", volume=" + volume + ", volumeClosed=" + volumeClosed + ", margin=" + margin + ", value=" + value + ", netDifference=" + netDifference + ", miscellaneous="
        + miscellaneous + ", orderFlags=" + orderFlags + ", volumeInQuoteCurrency=" + volumeInQuoteCurrency + "]";
  }

}

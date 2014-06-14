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
package com.xeiam.xchange.kraken.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.kraken.dto.trade.KrakenOrderType;
import com.xeiam.xchange.kraken.dto.trade.KrakenType;

public class KrakenPublicTrade {

  private final BigDecimal price;
  private final BigDecimal volume;
  private final double time;
  private final KrakenType type;
  private final KrakenOrderType orderType;
  private final String miscellaneous;

  public KrakenPublicTrade(@JsonProperty("price") BigDecimal price, @JsonProperty("volume") BigDecimal volume, @JsonProperty("time") double time, @JsonProperty("type") KrakenType type,
      @JsonProperty("orderType") KrakenOrderType orderType, @JsonProperty("miscellaneous") String miscellaneous) {

    this.price = price;
    this.volume = volume;
    this.time = time;
    this.type = type;
    this.orderType = orderType;
    this.miscellaneous = miscellaneous;
  }

  public BigDecimal getPrice() {

    return price;
  }

  public BigDecimal getVolume() {

    return volume;
  }

  public double getTime() {

    return time;
  }

  public KrakenType getType() {

    return type;
  }

  public KrakenOrderType getOrderType() {

    return orderType;
  }

  public String getMiscellaneous() {

    return miscellaneous;
  }

  @Override
  public String toString() {

    return "KrakenPublicTrade [price=" + price + ", volume=" + volume + ", time=" + time + ", type=" + type + ", orderType=" + orderType + ", miscellaneous=" + miscellaneous + "]";
  }

}

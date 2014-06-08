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
package com.xeiam.xchange.cryptsy.dto.marketdata;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author ObsessiveOrange
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class CryptsyOrderBook {

  private final int marketId;
  private final List<CryptsyBuyOrder> buyOrders;
  private final List<CryptsySellOrder> sellOrders;

  /**
   * Constructor
   */
  @JsonCreator
  public CryptsyOrderBook(@JsonProperty("marketid") int marketId, @JsonProperty("buyorders") List<CryptsyBuyOrder> buyOrders, @JsonProperty("sellorders") List<CryptsySellOrder> sellOrders) {

    this.marketId = marketId;
    this.buyOrders = buyOrders;
    this.sellOrders = sellOrders;
  }

  public int marketId() {

    return marketId;
  }

  public List<CryptsyBuyOrder> buyOrders() {

    return buyOrders;
  }

  public List<CryptsySellOrder> sellOrders() {

    return sellOrders;
  }

  @Override
  public String toString() {

    return "CryptsyOrderBook [Market ID='" + marketId + "',Buy Orders='" + buyOrders + "',Sell Orders=" + sellOrders + "]";
  }

}

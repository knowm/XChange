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
import com.fasterxml.jackson.annotation.JsonProperty;

public class CryptsyPublicOrderbook {

  private final int marketId;
  private final String label;
  private final String priCurrCode;
  private final String priCurrName;
  private final String secCurrCode;
  private final String secCurrName;
  private final List<CryptsyPublicOrder> buyOrders;
  private final List<CryptsyPublicOrder> sellOrders;

  /**
   * Constructor
   */
  @JsonCreator
  public CryptsyPublicOrderbook(@JsonProperty("marketid") int marketId, @JsonProperty("label") String label, @JsonProperty("primarycode") String priCurrCode,
      @JsonProperty("primaryname") String priCurrName, @JsonProperty("secondarycode") String secCurrCode, @JsonProperty("secondaryname") String secCurrName,
      @JsonProperty("buyorders") List<CryptsyPublicOrder> buyOrders, @JsonProperty("sellorders") List<CryptsyPublicOrder> sellOrders) {

    this.marketId = marketId;
    this.label = label;
    this.priCurrCode = priCurrCode;
    this.priCurrName = priCurrName;
    this.secCurrCode = secCurrCode;
    this.secCurrName = secCurrName;
    this.buyOrders = buyOrders;
    this.sellOrders = sellOrders;
  }

  public int getMarketId() {

    return marketId;
  }

  public String getLabel() {

    return label;
  }

  public String getPriCurrCode() {

    return priCurrCode;
  }

  public String getPriCurrName() {

    return priCurrName;
  }

  public String getSecCurrCode() {

    return secCurrCode;
  }

  public String getSecCurrName() {

    return secCurrName;
  }

  public List<CryptsyPublicOrder> getBuyOrders() {

    return buyOrders;
  }

  public List<CryptsyPublicOrder> getSellOrders() {

    return sellOrders;
  }

  @Override
  public String toString() {

    return "CryptsyPublicOrderbook [marketId=" + marketId + ", label=" + label + ", priCurrCode=" + priCurrCode + ", priCurrName=" + priCurrName + ", secCurrCode=" + secCurrCode + ", secCurrName="
        + secCurrName + ", buyOrders=" + buyOrders + ", sellOrders=" + sellOrders + "]";
  }

}

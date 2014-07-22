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
package com.xeiam.xchange.btcchina.dto.trade;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author David Yam
 */
public class BTCChinaOrders {

  private final List<BTCChinaOrder> orders;
  private final List<BTCChinaOrder> btcCnyOrders;
  private final List<BTCChinaOrder> ltcCnyOrders;
  private final List<BTCChinaOrder> ltcBtcOrders;

  /**
   * @param orders
   * @param btcCnyOrders
   * @param ltcCnyOrders
   * @param ltcBtcOrders
   */
  public BTCChinaOrders(
      @JsonProperty("order") List<BTCChinaOrder> orders,
      @JsonProperty("order_btccny") List<BTCChinaOrder> btcCnyOrders,
      @JsonProperty("order_ltccny") List<BTCChinaOrder> ltcCnyOrders,
      @JsonProperty("order_ltcbtc") List<BTCChinaOrder> ltcBtcOrders) {

    this.orders = orders;
    this.btcCnyOrders = btcCnyOrders;
    this.ltcCnyOrders = ltcCnyOrders;
    this.ltcBtcOrders = ltcBtcOrders;
  }

  public List<BTCChinaOrder> getOrders() {

    return orders;
  }

  public List<BTCChinaOrder> getBtcCnyOrders() {

    return btcCnyOrders;
  }

  public List<BTCChinaOrder> getLtcCnyOrders() {

    return ltcCnyOrders;
  }

  public List<BTCChinaOrder> getLtcBtcOrders() {

    return ltcBtcOrders;
  }

  @Override
  public String toString() {

    return String.format("BTCChinaOrders{orders=%s, btcCnyOrders=%s, ltcCnyorders=%s, ltcBtcOrders=%s}",
      orders, btcCnyOrders, ltcCnyOrders, ltcBtcOrders);
  }

}

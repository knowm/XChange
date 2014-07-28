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
package com.xeiam.xchange.btccentral.dto.marketdata;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author kpysniak
 */
public class BTCCentralMarketDepth {

  private final List<BTCCentralMarketOrder> bids;
  private final List<BTCCentralMarketOrder> asks;

  /**
   * @param bids
   * @param asks
   */
  public BTCCentralMarketDepth(@JsonProperty("bids") List<BTCCentralMarketOrder> bids, @JsonProperty("asks") List<BTCCentralMarketOrder> asks) {

    this.bids = bids;
    this.asks = asks;
  }

  public List<BTCCentralMarketOrder> getBids() {

    return bids;
  }

  public List<BTCCentralMarketOrder> getAsks() {

    return asks;
  }

  @Override
  public String toString() {

    return "BTCCentralMarketDepth{" + "bids=" + bids + ", asks=" + asks + '}';
  }
}

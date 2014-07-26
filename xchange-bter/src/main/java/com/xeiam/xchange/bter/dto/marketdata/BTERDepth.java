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
package com.xeiam.xchange.bter.dto.marketdata;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.bter.dto.BTERBaseResponse;

/**
 * Data object representing depth from Bter
 */
public class BTERDepth extends BTERBaseResponse {

  private final List<BTERPublicOrder> asks;
  private final List<BTERPublicOrder> bids;

  /**
   * Constructor
   * 
   * @param asks
   * @param bids
   */
  private BTERDepth(@JsonProperty("asks") List<BTERPublicOrder> asks, @JsonProperty("bids") List<BTERPublicOrder> bids, @JsonProperty("result") boolean result) {

    super(result, null);
    this.asks = asks;
    this.bids = bids;
  }

  public List<BTERPublicOrder> getAsks() {

    return asks;
  }

  public List<BTERPublicOrder> getBids() {

    return bids;
  }

  @Override
  public String toString() {

    return "BTERDepth [asks=" + asks.toString() + ", bids=" + bids.toString() + "]";
  }

}

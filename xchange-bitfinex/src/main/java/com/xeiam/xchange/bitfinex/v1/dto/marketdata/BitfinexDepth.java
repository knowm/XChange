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
package com.xeiam.xchange.bitfinex.v1.dto.marketdata;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BitfinexDepth {

  private final BitfinexLevel[] asks;
  private final BitfinexLevel[] bids;

  /**
   * Constructor
   * 
   * @param asks
   * @param bids
   */
  public BitfinexDepth(@JsonProperty("asks") BitfinexLevel[] asks, @JsonProperty("bids") BitfinexLevel[] bids) {

    this.asks = asks;
    this.bids = bids;
  }

  public BitfinexLevel[] getAsks() {

    return asks;
  }

  public BitfinexLevel[] getBids() {

    return bids;
  }

  @Override
  public String toString() {

    return "BitfinexDepth [asks=" + Arrays.toString(asks) + ", bids=" + Arrays.toString(bids) + "]";
  }

}

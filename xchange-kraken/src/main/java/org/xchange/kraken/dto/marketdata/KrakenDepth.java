/**
 * Copyright (C) 2012 - 2013 Xeiam LLC http://xeiam.com
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
package org.xchange.kraken.dto.marketdata;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data object representing depth from BTCE
 */
public class KrakenDepth {

  private final List<KrakenOrder> asks;
  private final List<KrakenOrder> bids;

  /**
   * Constructor
   * 
   * @param asks
   * @param bids
   */
  public KrakenDepth(@JsonProperty("asks") List<Object[]> asks, @JsonProperty("bids") List<Object[]> bids) {

    this.asks = new ArrayList<KrakenOrder>();
    this.bids = new ArrayList<KrakenOrder>();

    for (Object[] order : asks) {
      this.asks.add(new KrakenOrder(order));
    }
    for (Object[] order : bids) {
      this.bids.add(new KrakenOrder(order));
    }
  }

  public List<KrakenOrder> getAsks() {

    return asks;
  }

  public List<KrakenOrder> getBids() {

    return bids;
  }

  @Override
  public String toString() {
    return "KrakenDepth [asks=" + asks.toString() + ", bids=" + bids.toString() + "]";
  }

}

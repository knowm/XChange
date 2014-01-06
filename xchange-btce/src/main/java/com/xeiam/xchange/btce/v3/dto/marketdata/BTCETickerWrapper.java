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
package com.xeiam.xchange.btce.v3.dto.marketdata;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Author: brox
 * Since: 11/12/13 11:00 PM
 * Data object representing multi-currency market data from BTCE API v.3
 */
public class BTCETickerWrapper {

  private final Map<String, BTCETicker> tickerMap;

  /**
   * Constructor
   * 
   * @param resultV3
   */
  @JsonCreator
  public BTCETickerWrapper(Map<String, BTCETicker> resultV3) {

    this.tickerMap = resultV3;
  }

  public Map<String, BTCETicker> getTickerMap() {

    return tickerMap;
  }

  public BTCETicker getTicker(String tradableIdentifier, String currency) {

    String pair = com.xeiam.xchange.btce.v3.BTCEUtils.getPair(tradableIdentifier, currency);
    BTCETicker result = null;
    if (tickerMap.containsKey(pair)) {
      result = tickerMap.get(pair);
    }
    return result;
  }

  @Override
  public String toString() {

    return "BTCETickerV3 [map=" + tickerMap.toString() + "]";
  }

}

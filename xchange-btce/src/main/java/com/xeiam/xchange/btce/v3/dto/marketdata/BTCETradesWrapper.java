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
package com.xeiam.xchange.btce.v3.dto.marketdata;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Author: brox
 * Data object representing multi-currency trades from BTCE API v.3
 */
public class BTCETradesWrapper {

  private final Map<String, BTCETrade[]> tradesMap;

  /**
   * Constructor
   * 
   * @param tradesMap
   */
  @JsonCreator
  public BTCETradesWrapper(Map<String, BTCETrade[]> tradesMap) {

    this.tradesMap = tradesMap;
  }

  public Map<String, BTCETrade[]> getTradesMap() {

    return tradesMap;
  }

  public BTCETrade[] getTrades(String pair) {

    BTCETrade[] result = null;
    if (tradesMap.containsKey(pair)) {
      result = tradesMap.get(pair);
    }
    return result;
  }

  @Override
  public String toString() {

    return "BTCETradesV3 [map=" + tradesMap.toString() + "]";
  }

}

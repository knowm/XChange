/**
 * Copyright (C) 2013 Matija Mazi
 * Copyright (C) 2013 Xeiam LLC http://xeiam.com
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
package com.xeiam.xchange.bitstamp.api.model;

/**
 * @author Matija Mazi <br/>
 * @created 4/20/12 7:12 PM
 */
public class Ticker {

  double last;
  double high;
  double low;
  double volume;
  double bid;
  double ask;

  public double getLast() {

    return last;
  }

  public double getHigh() {

    return high;
  }

  public double getLow() {

    return low;
  }

  public double getVolume() {

    return volume;
  }

  public double getBid() {

    return bid;
  }

  public double getAsk() {

    return ask;
  }

  @Override
  public String toString() {

    return String.format("Ticker{last=%s, high=%s, low=%s, volume=%s, bid=%s, ask=%s}", last, high, low, volume, bid, ask);
  }
}

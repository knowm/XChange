/**
 * Copyright (C) 2012 Xeiam LLC http://xeiam.com
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
package com.xeiam.xchange.virtex.dto.marketdata;

/**
 * Data object representing Ticker from VirtEx
 */
// todo make immutable. see MtGoxTicker.
public class VirtExTicker {

  public float last;
  public float bid;
  public float ask;
  public float high;
  public float low;
  public float volume;

  public float getLast() {

    return last;
  }

  public void setLast(float last) {

    this.last = last;
  }

  public float getBid() {

    return bid;
  }

  public void setBid(float bid) {

    this.bid = bid;
  }

  public float getAsk() {

    return ask;
  }

  public void setAsk(float ask) {

    this.ask = ask;
  }

  public float getHigh() {

    return high;
  }

  public void setHigh(float high) {

    this.high = high;
  }

  public float getLow() {

    return low;
  }

  public void setLow(float low) {

    this.low = low;
  }

  public float getVolume() {

    return volume;
  }

  public void setVolume(float volume) {

    this.volume = volume;
  }

  @Override
  public String toString() {

    return "VirtExTicker [last=" + last + ", bid=" + bid + ", ask=" + ask + ", high=" + high + ", low=" + low + ", volume=" + volume + "]";
  }

}

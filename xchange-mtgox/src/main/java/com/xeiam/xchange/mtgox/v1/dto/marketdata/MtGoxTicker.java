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
package com.xeiam.xchange.mtgox.v1.dto.marketdata;

import com.xeiam.xchange.mtgox.v1.dto.MtGoxValue;

/**
 * Data object representing Ticker from Mt Gox
 */
public class MtGoxTicker {

  public MtGoxValue high = new MtGoxValue();
  public MtGoxValue low = new MtGoxValue();
  public MtGoxValue avg = new MtGoxValue();
  public MtGoxValue vwap = new MtGoxValue();
  public MtGoxValue vol = new MtGoxValue();
  public MtGoxValue last_local = new MtGoxValue();
  public MtGoxValue last = new MtGoxValue();
  public MtGoxValue last_orig = new MtGoxValue();
  public MtGoxValue last_all = new MtGoxValue();
  public MtGoxValue buy = new MtGoxValue();
  public MtGoxValue sell = new MtGoxValue();

  public MtGoxValue getHigh() {
    return high;
  }

  public void setHigh(MtGoxValue high) {
    this.high = high;
  }

  public MtGoxValue getLow() {
    return low;
  }

  public void setLow(MtGoxValue low) {
    this.low = low;
  }

  public MtGoxValue getAvg() {
    return avg;
  }

  public void setAvg(MtGoxValue avg) {
    this.avg = avg;
  }

  public MtGoxValue getVwap() {
    return vwap;
  }

  public void setVwap(MtGoxValue vwap) {
    this.vwap = vwap;
  }

  public MtGoxValue getVol() {
    return vol;
  }

  public void setVol(MtGoxValue vol) {
    this.vol = vol;
  }

  public MtGoxValue getLast_local() {
    return last_local;
  }

  public void setLast_local(MtGoxValue last_local) {
    this.last_local = last_local;
  }

  public MtGoxValue getLast() {
    return last;
  }

  public void setLast(MtGoxValue last) {
    this.last = last;
  }

  public MtGoxValue getLast_orig() {
    return last_orig;
  }

  public void setLast_orig(MtGoxValue last_orig) {
    this.last_orig = last_orig;
  }

  public MtGoxValue getLast_all() {
    return last_all;
  }

  public void setLast_all(MtGoxValue last_all) {
    this.last_all = last_all;
  }

  public MtGoxValue getBuy() {
    return buy;
  }

  public void setBuy(MtGoxValue buy) {
    this.buy = buy;
  }

  public MtGoxValue getSell() {
    return sell;
  }

  public void setSell(MtGoxValue sell) {
    this.sell = sell;
  }

}

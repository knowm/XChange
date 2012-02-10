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
package com.xeiam.xchange.mtgox.v1.service.marketdata.dto;

/**
 * Data object representing Ticker from Mt Gox
 */
public class MtGoxTicker {

  public String result;
  public Return _return;

  public class Return {

    public MtGoxTick high = new MtGoxTick();
    public MtGoxTick low = new MtGoxTick();
    public MtGoxTick avg = new MtGoxTick();
    public MtGoxTick vwap = new MtGoxTick();
    public MtGoxTick vol = new MtGoxTick();
    public MtGoxTick last_local = new MtGoxTick();
    public MtGoxTick last = new MtGoxTick();
    public MtGoxTick last_orig = new MtGoxTick();
    public MtGoxTick last_all = new MtGoxTick();
    public MtGoxTick buy = new MtGoxTick();
    public MtGoxTick sell = new MtGoxTick();

    public MtGoxTick getHigh() {
      return high;
    }

    public void setHigh(MtGoxTick high) {
      this.high = high;
    }

    public MtGoxTick getLow() {
      return low;
    }

    public void setLow(MtGoxTick low) {
      this.low = low;
    }

    public MtGoxTick getAvg() {
      return avg;
    }

    public void setAvg(MtGoxTick avg) {
      this.avg = avg;
    }

    public MtGoxTick getVwap() {
      return vwap;
    }

    public void setVwap(MtGoxTick vwap) {
      this.vwap = vwap;
    }

    public MtGoxTick getVol() {
      return vol;
    }

    public void setVol(MtGoxTick vol) {
      this.vol = vol;
    }

    public MtGoxTick getLast_local() {
      return last_local;
    }

    public void setLast_local(MtGoxTick last_local) {
      this.last_local = last_local;
    }

    public MtGoxTick getLast() {
      return last;
    }

    public void setLast(MtGoxTick last) {
      this.last = last;
    }

    public MtGoxTick getLast_orig() {
      return last_orig;
    }

    public void setLast_orig(MtGoxTick last_orig) {
      this.last_orig = last_orig;
    }

    public MtGoxTick getLast_all() {
      return last_all;
    }

    public void setLast_all(MtGoxTick last_all) {
      this.last_all = last_all;
    }

    public MtGoxTick getBuy() {
      return buy;
    }

    public void setBuy(MtGoxTick buy) {
      this.buy = buy;
    }

    public MtGoxTick getSell() {
      return sell;
    }

    public void setSell(MtGoxTick sell) {
      this.sell = sell;
    }

  }

  public String getResult() {
    return result;
  }

  public void setResult(String result) {
    this.result = result;
  }

  public Return getReturn() {
    return _return;
  }

  public void setReturn(Return _return) {
    this._return = _return;
  }

}

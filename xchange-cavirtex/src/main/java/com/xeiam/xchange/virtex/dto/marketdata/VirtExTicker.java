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

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Data object representing Ticker from VirtEx
 * 
 * @immutable
 */

public final class VirtExTicker {

  public final float last;
  public final float high;
  public final float low;
  public final float volume;
  //public float bid; //Value does not exist on VirtEx Ticker API
  //public float ask; //Value does not exist on VirtEx Ticker API
  
  /**
   * Constructor
   * 
   * @param high
   * @param low
   * @param volume
   * @param last
   */
  
  public VirtExTicker(@JsonProperty("high") float high, @JsonProperty("low") float low, @JsonProperty("volume") float volume, @JsonProperty("last") float last) {

	    this.high = high;
	    this.low = low;
	    this.volume = volume;
	    this.last = last;
	  }
	  
  public float getLast() {

    return last;
  }

/*

  public void setBid(float bid) {

    this.bid = bid;
  }

  public void setAsk(float ask) {

    this.ask = ask;
  }
 */

  public float getHigh() {

    return high;
  }

  public float getLow() {

    return low;
  }

  public float getVolume() {

    return volume;
  }

  @Override
  public String toString() {

    //return "VirtExTicker [last=" + last + ", bid=" + bid + ", ask=" + ask + ", high=" + high + ", low=" + low + ", volume=" + volume + "]";
	return "VirtExTicker [last=" + last + ", high=" + high + ", low=" + low + ", volume=" + volume + "]";

  }

}

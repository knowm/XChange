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

import java.math.BigDecimal;


/**
 * Data object representing an order of kraken exchange
 */
public class KrakenOrder {
  
  private final BigDecimal price;
  private final BigDecimal volume;
  private final long timestamp;
  
  public KrakenOrder(Object[] order) {
    this.price = new BigDecimal((String) order[0]);
    this.volume = new BigDecimal((String) order[1]);
    this.timestamp = ((Integer) order[2]).longValue();
  }
  
  public BigDecimal getPrice() {
    return price;
  }
  
  public BigDecimal getVolume() {
    return volume;
  }
  
  public long getTimestamp() {
    return timestamp;
  }
  
  public String toString() {
    return "[price=" + price.toString() + ", volume=" + volume.toString() + ", timestamp=" + timestamp + "]";
  }
}

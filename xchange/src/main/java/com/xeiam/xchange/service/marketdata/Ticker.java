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
package com.xeiam.xchange.service.marketdata;

import com.xeiam.xchange.SymbolPair;
import com.xeiam.xchange.utils.DateUtils;
import net.jcip.annotations.Immutable;
import org.joda.money.BigMoney;
import org.joda.time.DateTime;

/**
 * A class encapsulating the most basic information a "Ticker" should contain. A ticker contains data representing the latest trade. This class is immutable.
 */
@Immutable
public final class Ticker {

  // TODO add timestamp? Only if exchanges provide it. So far MtGox doesn't. If several provide it, let's add it and set it null as default.
  private final DateTime timestamp;
  private final BigMoney last;
  private final SymbolPair symbolPair;
  private final long volume;

  /**
   * Constructor
   * 
   * @param last
   * @param symbolPair
   * @param volume
   */
  public Ticker(BigMoney last, SymbolPair symbolPair, long volume) {
    this.last = last;
    this.symbolPair = symbolPair;
    this.volume = volume;
    this.timestamp = DateUtils.nowUtc();
  }

  public BigMoney getLast() {
    return last;
  }

  public SymbolPair getSymbolPair() {
    return symbolPair;
  }

  public long getVolume() {
    return volume;
  }

  @Override
  public String toString() {
    return "Ticker [last=" + last + ", symbolPair=" + symbolPair + ", volume=" + volume + "]";
  }

}

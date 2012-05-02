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

import com.xeiam.xchange.CurrencyPair;
import com.xeiam.xchange.utils.DateUtils;
import net.jcip.annotations.Immutable;
import org.joda.money.BigMoney;
import org.joda.time.DateTime;

/**
 * A class encapsulating the most basic information a "Ticker" should contain. A ticker contains data representing the latest trade. This class is immutable.
 */
@Immutable
public final class Ticker {

  private final DateTime timestamp;
  private final BigMoney last;
  private final BigMoney bid;
  private final BigMoney ask;
  private final CurrencyPair symbolPair;
  private final long volume;

  /**
   * Constructor
   * 
   * @param last
   * @param symbolPair
   * @param volume
   */
  public Ticker(BigMoney last, BigMoney bid, BigMoney ask, CurrencyPair symbolPair, long volume) {
    this.last = last;
    this.bid = bid;
    this.ask = ask;
    this.symbolPair = symbolPair;
    this.volume = volume;
    this.timestamp = DateUtils.nowUtc();
  }

  public BigMoney getLast() {
    return last;
  }

  public BigMoney getBid() {
    return bid;
  }

  public BigMoney getAsk() {
    return ask;
  }

  public CurrencyPair getSymbolPair() {
    return symbolPair;
  }

  public long getVolume() {
    return volume;
  }

  @Override
  public String toString() {
    return "Ticker{" + "timestamp=" + timestamp + ", last=" + last + ", bid=" + bid + ", ask=" + ask + ", symbolPair=" + symbolPair + ", volume=" + volume + '}';
  }
}

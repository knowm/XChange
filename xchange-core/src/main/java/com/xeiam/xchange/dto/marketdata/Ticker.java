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
package com.xeiam.xchange.dto.marketdata;

import java.math.BigDecimal;

import org.joda.money.BigMoney;
import org.joda.time.DateTime;

import com.xeiam.xchange.utils.DateUtils;

/**
 * <p>
 * A class encapsulating the most basic information a "Ticker" should contain.
 * </p>
 * <p>
 * A ticker contains data representing the latest trade.
 * </p>
 */

public final class Ticker {

  private String tradableIdentifier;
  private BigMoney last;
  private BigMoney bid;
  private BigMoney ask;
  private BigMoney high;
  private BigMoney low;
  private BigDecimal volume;
  private DateTime timestamp;

  private ErrorMessage errorMessage;

  /**
   * Prevent arbitrary construction outside of the Builder
   */
  Ticker() {

  }

  /**
   * Constructor
   * 
   * @param tradableIdentifier
   * @param last
   * @param bid
   * @param ask
   * @param high
   * @param low
   * @param volume Note: Too many parameters for this constructor - refactored to use Builder
   * @deprecated This constructor will be removed in 1.4.0
   */
  @Deprecated
  public Ticker(String tradableIdentifier, BigMoney last, BigMoney bid, BigMoney ask, BigMoney high, BigMoney low, BigDecimal volume) {

    this.tradableIdentifier = tradableIdentifier;
    this.last = last;
    this.bid = bid;
    this.ask = ask;
    this.high = high;
    this.low = low;
    this.volume = volume;
    this.timestamp = DateUtils.nowUtc();
  }

  public String getTradableIdentifier() {

    return tradableIdentifier;
  }

  /* package */void setTradableIdentifier(String tradableIdentifier) {

    this.tradableIdentifier = tradableIdentifier;
  }

  public BigMoney getLast() {

    return last;
  }

  /* package */void setLast(BigMoney last) {

    this.last = last;
  }

  public BigMoney getBid() {

    return bid;
  }

  /* package */void setBid(BigMoney bid) {

    this.bid = bid;
  }

  public BigMoney getAsk() {

    return ask;
  }

  /* package */void setAsk(BigMoney ask) {

    this.ask = ask;
  }

  public BigMoney getHigh() {

    return high;
  }

  /* package */void setHigh(BigMoney high) {

    this.high = high;
  }

  public BigMoney getLow() {

    return low;
  }

  /* package */void setLow(BigMoney low) {

    this.low = low;
  }

  public BigDecimal getVolume() {

    return volume;
  }

  /* package */void setVolume(BigDecimal volume) {

    this.volume = volume;
  }

  public DateTime getTimestamp() {

    return timestamp;
  }

  /* package */void setTimestamp(DateTime timestamp) {

    this.timestamp = timestamp;
  }

  public ErrorMessage getErrorMessage() {

    return errorMessage;
  }

  /* package */void setErrorMessage(ErrorMessage errorMessage) {

    this.errorMessage = errorMessage;
  }

  @Override
  public String toString() {

    return "Ticker [tradableIdentifier=" + tradableIdentifier + ", last=" + last + ", bid=" + bid + ", ask=" + ask + ", high=" + high + ", low=" + low + ", volume=" + volume + ", timestamp="
        + timestamp + "]";
  }

}

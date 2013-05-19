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

import org.joda.money.BigMoney;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * A class encapsulating the most basic information a "Ticker" should contain.
 * </p>
 * <p>
 * A ticker contains data representing the latest trade.
 * </p>
 */
public final class Ticker {

  private final String tradableIdentifier;
  private final BigMoney last;
  private final BigMoney bid;
  private final BigMoney ask;
  private final BigMoney high;
  private final BigMoney low;
  private final BigDecimal volume;
  private final Date timestamp;

  /**
   * Constructor
   *
   * @param tradableIdentifier The tradable identifier (e.g. BTC in BTC/USD)
   * @param last
   * @param bid
   * @param ask
   * @param high
   * @param low
   * @param volume
   * @param timestamp
   */
  private Ticker(String tradableIdentifier, BigMoney last, BigMoney bid, BigMoney ask, BigMoney high, BigMoney low, BigDecimal volume, Date timestamp) {

    this.tradableIdentifier = tradableIdentifier;
    this.last = last;
    this.bid = bid;
    this.ask = ask;
    this.high = high;
    this.low = low;
    this.volume = volume;
    this.timestamp = timestamp;
  }

  public String getTradableIdentifier() {

    return tradableIdentifier;
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

  public BigMoney getHigh() {

    return high;
  }

  public BigMoney getLow() {

    return low;
  }

  public BigDecimal getVolume() {

    return volume;
  }

  public Date getTimestamp() {

    return timestamp;
  }

  @Override
  public String toString() {

    return "Ticker [tradableIdentifier=" + tradableIdentifier + ", last=" + last + ", bid=" + bid + ", ask=" + ask + ", high=" + high + ", low=" + low + ", volume=" + volume + ", timestamp="
        + timestamp + "]";
  }

  /**
   * <p>
   * Builder to provide the following to {@link Ticker}:
   * </p>
   * <ul>
   * <li>Provision of fluent chained construction interface</li>
   * </ul>
   *  
   */
  public static class TickerBuilder {

    private String tradableIdentifier;
    private BigMoney last;
    private BigMoney bid;
    private BigMoney ask;
    private BigMoney high;
    private BigMoney low;
    private BigDecimal volume;
    private Date timestamp;

    /**
     * @return A new instance of the builder
     */
    public static TickerBuilder newInstance() {

      return new TickerBuilder();
    }

    // Prevent repeat builds
    private boolean isBuilt = false;

    public Ticker build() {

      validateState();

      Ticker ticker = new Ticker(tradableIdentifier, last, bid, ask, high, low, volume, timestamp);

      isBuilt = true;

      return ticker;
    }

    private void validateState() {

      if (isBuilt) {
        throw new IllegalStateException("The entity has been built");
      }
    }

    public TickerBuilder withTradableIdentifier(String tradableIdentifier) {

      this.tradableIdentifier = tradableIdentifier;
      return this;
    }

    public TickerBuilder withLast(BigMoney last) {

      this.last = last;
      return this;
    }

    public TickerBuilder withBid(BigMoney bid) {

      this.bid = bid;
      return this;
    }

    public TickerBuilder withAsk(BigMoney ask) {

      this.ask = ask;
      return this;
    }

    public TickerBuilder withHigh(BigMoney high) {

      this.high = high;
      return this;
    }

    public TickerBuilder withLow(BigMoney low) {

      this.low = low;
      return this;
    }

    public TickerBuilder withVolume(BigDecimal volume) {

      this.volume = volume;
      return this;
    }

    public TickerBuilder withTimestamp(Date timestamp) {

      this.timestamp = timestamp;
      return this;
    }

  }
}
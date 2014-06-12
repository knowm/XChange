/**
 * Copyright (C) 2012 - 2014 Xeiam LLC http://xeiam.com
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

import com.google.common.collect.Maps;
import com.xeiam.xchange.currency.CurrencyPair;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * <p>
 * A class encapsulating the most basic information a "Ticker" should contain.
 * </p>
 * <p>
 * A ticker contains data representing the latest trade.
 * </p>
 */
public final class Ticker {

  private final CurrencyPair currencyPair;
  private final BigDecimal last;
  private final BigDecimal bid;
  private final BigDecimal ask;
  private final BigDecimal high;
  private final BigDecimal low;
  private final BigDecimal volume;
  private final Date timestamp;
  private final Map<String, Object> attributes;

  /**
   * Constructor
   *
   * @param currencyPair The tradable identifier (e.g. BTC in BTC/USD)
   * @param last
   * @param bid
   * @param ask
   * @param high
   * @param low
   * @param volume 24h volume
   * @param timestamp
   */
  private Ticker(CurrencyPair currencyPair, BigDecimal last, BigDecimal bid, BigDecimal ask, BigDecimal high, BigDecimal low, BigDecimal volume, Date timestamp, Map<String, Object> attributes) {

    this.currencyPair = currencyPair;
    this.last = last;
    this.bid = bid;
    this.ask = ask;
    this.high = high;
    this.low = low;
    this.volume = volume;
    this.timestamp = timestamp;
    this.attributes = attributes;
  }

  public CurrencyPair getCurrencyPair() {

    return currencyPair;
  }

  public BigDecimal getLast() {

    return last;
  }

  public BigDecimal getBid() {

    return bid;
  }

  public BigDecimal getAsk() {

    return ask;
  }

  public BigDecimal getHigh() {

    return high;
  }

  public BigDecimal getLow() {

    return low;
  }

  public BigDecimal getVolume() {

    return volume;
  }

  public Date getTimestamp() {

    return timestamp;
  }

  public Object getAttribute(String attributeName) {

    return attributes.get(attributeName);
  }

  @Override
  public String toString() {

    return "Ticker [currencyPair=" + currencyPair + ", last=" + last + ", bid=" + bid + ", ask=" + ask + ", high=" + high + ", low=" + low + ", volume=" + volume + ", timestamp=" + timestamp + ", attributes=" + attributes.toString() +"]";
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

    private CurrencyPair currencyPair;
    private BigDecimal last;
    private BigDecimal bid;
    private BigDecimal ask;
    private BigDecimal high;
    private BigDecimal low;
    private BigDecimal volume;
    private Date timestamp;
    private Map<String, Object> attributes = Maps.newHashMap();

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

      Ticker ticker = new Ticker(currencyPair, last, bid, ask, high, low, volume, timestamp, attributes);

      isBuilt = true;

      return ticker;
    }

    private void validateState() {

      if (isBuilt) {
        throw new IllegalStateException("The entity has been built");
      }
    }

    public TickerBuilder withCurrencyPair(CurrencyPair currencyPair) {

      this.currencyPair = currencyPair;
      return this.withAttribute("currencyPair", currencyPair);
    }

    public TickerBuilder withLast(BigDecimal last) {

      this.last = last;
      return this.withAttribute("last", last);
    }

    public TickerBuilder withBid(BigDecimal bid) {

      this.bid = bid;
      return this.withAttribute("bid", bid);
    }

    public TickerBuilder withAsk(BigDecimal ask) {

      this.ask = ask;
      return this.withAttribute("ask", ask);
    }

    public TickerBuilder withHigh(BigDecimal high) {

      this.high = high;
      return this.withAttribute("high", high);
    }

    public TickerBuilder withLow(BigDecimal low) {

      this.low = low;
      return this.withAttribute("low", low);
    }

    public TickerBuilder withVolume(BigDecimal volume) {

      this.volume = volume;
      return this.withAttribute("volume", volume);
    }

    public TickerBuilder withTimestamp(Date timestamp) {

      this.timestamp = timestamp;
      return this.withAttribute("timestamp", timestamp);
    }

    public TickerBuilder withAttribute(String attributeName, Object attributeValue) {

      this.attributes.put(attributeName, attributeValue);
      return this;
    }

  }
}
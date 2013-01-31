/**
 * Copyright (C) 2013 Xeiam LLC http://xeiam.com
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
package com.xeiam.xchange.bitcoincentral.dto.marketdata;

import java.math.BigDecimal;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author timmolter
 */
public final class BitcoinCentralTicker {

  private final BigDecimal ask;
  private final BigDecimal at;
  private final BigDecimal bid;
  private final String currency;
  private final BigDecimal high;
  private final BigDecimal low;
  private final BigDecimal midpoint;
  private final BigDecimal price;
  private final BigDecimal variation;
  private final BigDecimal volume;

  public BitcoinCentralTicker(@JsonProperty("ask") BigDecimal ask, @JsonProperty("at") BigDecimal at, @JsonProperty("bid") BigDecimal bid, @JsonProperty("currency") String currency,
      @JsonProperty("high") BigDecimal high, @JsonProperty("low") BigDecimal low, @JsonProperty("midpoint") BigDecimal midpoint, @JsonProperty("price") BigDecimal price,
      @JsonProperty("variation") BigDecimal variation, @JsonProperty("volume") BigDecimal volume) {

    this.ask = ask;
    this.at = at;
    this.bid = bid;
    this.currency = currency;
    this.high = high;
    this.low = low;
    this.midpoint = midpoint;
    this.price = price;
    this.variation = variation;
    this.volume = volume;
  }

  public BigDecimal getAsk() {

    return this.ask;
  }

  public BigDecimal getAt() {

    return this.at;
  }

  public BigDecimal getBid() {

    return this.bid;
  }

  public String getCurrency() {

    return this.currency;
  }

  public BigDecimal getHigh() {

    return this.high;
  }

  public BigDecimal getLow() {

    return this.low;
  }

  public BigDecimal getMidpoint() {

    return this.midpoint;
  }

  public BigDecimal getPrice() {

    return this.price;
  }

  public BigDecimal getVariation() {

    return this.variation;
  }

  public BigDecimal getVolume() {

    return this.volume;
  }

  @Override
  public String toString() {

    return "BitcoinCentralTicker [ask=" + ask + ", at=" + at + ", bid=" + bid + ", currency=" + currency + ", high=" + high + ", low=" + low + ", midpoint=" + midpoint + ", price=" + price
        + ", variation=" + variation + ", volume=" + volume + "]";
  }

}

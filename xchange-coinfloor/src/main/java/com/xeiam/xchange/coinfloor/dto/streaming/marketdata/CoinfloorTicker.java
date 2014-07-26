/**
 * The MIT License
 * Copyright (c) 2014 Xeiam LLC http://xeiam.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.xeiam.xchange.coinfloor.dto.streaming.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.coinfloor.CoinfloorUtils;
import com.xeiam.xchange.coinfloor.CoinfloorUtils.CoinfloorCurrency;

/**
 * @author obsessiveOrange
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class CoinfloorTicker {

  private final int tag;
  private final int errorCode;
  private final CoinfloorCurrency base;
  private final CoinfloorCurrency counter;
  private final BigDecimal last;
  private final BigDecimal bid;
  private final BigDecimal ask;
  private final BigDecimal low;
  private final BigDecimal high;
  private final BigDecimal volume;

  public CoinfloorTicker(@JsonProperty("tag") int tag, @JsonProperty("error_code") int errorCode, @JsonProperty("base") int base, @JsonProperty("counter") int counter, @JsonProperty("last") int last,
      @JsonProperty("bid") int bid, @JsonProperty("ask") int ask, @JsonProperty("low") int low, @JsonProperty("high") int high, @JsonProperty("volume") int volume) {

    this.tag = tag;
    this.errorCode = errorCode;
    this.base = base == 0 ? CoinfloorCurrency.BTC : CoinfloorUtils.getCurrency(base);
    this.counter = counter == 0 ? CoinfloorCurrency.GBP : CoinfloorUtils.getCurrency(counter);
    this.last = CoinfloorUtils.scalePriceToBigDecimal(this.base, this.counter, last);
    this.bid = CoinfloorUtils.scalePriceToBigDecimal(this.base, this.counter, bid);
    this.ask = CoinfloorUtils.scalePriceToBigDecimal(this.base, this.counter, ask);
    this.low = CoinfloorUtils.scalePriceToBigDecimal(this.base, this.counter, low);
    this.high = CoinfloorUtils.scalePriceToBigDecimal(this.base, this.counter, high);
    this.volume = CoinfloorUtils.scaleToBigDecimal(CoinfloorCurrency.BTC, volume);
  }

  public int getTag() {

    return tag;
  }

  public int getErrorCode() {

    return errorCode;
  }

  public CoinfloorCurrency getBase() {

    return base;
  }

  public CoinfloorCurrency getCounter() {

    return counter;
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

  public BigDecimal getLow() {

    return low;
  }

  public BigDecimal getHigh() {

    return high;
  }

  public BigDecimal getVolume() {

    return volume;
  }

  @Override
  public String toString() {

    return "CoinfloorTicker{tag='" + tag + "', errorcode='" + errorCode + "', last='" + last + "', bid='" + bid + "', ask='" + ask + "', low='" + low + "', volume='" + volume + "'}";
  }
}

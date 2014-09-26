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
package com.xeiam.xchange.huobi.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

/**
 * Data object representing Ticker from Mt Gox
 */
public final class HuobiTicker {
/*
    {
        "ticker": {
                "high": "3794.9",
                "low": "3678",
                "last": "3716.06",
                "vol": 37448.2016,
                "buy": "3716",
                "sell": "3716.06"
        },
        "time": 1403270952
    }
*/

  private final BigDecimal high;
  private final BigDecimal low;
  private final BigDecimal last;
  private final BigDecimal vol;
  private final BigDecimal buy;
  private final BigDecimal sell;


    public HuobiTicker(@JsonProperty("last") BigDecimal last,
                       @JsonProperty("buy") BigDecimal buy,
                       @JsonProperty("sell") BigDecimal sell,
                       @JsonProperty("high") BigDecimal high,
                       @JsonProperty("low") BigDecimal low,
                       @JsonProperty("vol") BigDecimal vol) {
        this.last = last;
        this.buy = buy;
        this.sell = sell;
        this.high = high;
        this.low = low;
        this.vol = vol;
    }

    public BigDecimal getLast() {
        return last;
    }

    public BigDecimal getBuy() {
        return buy;
    }

    public BigDecimal getSell() {
        return sell;
    }

    public BigDecimal getHigh() {
        return high;
    }

    public BigDecimal getLow() {
        return low;
    }

    public BigDecimal getVol() {
        return vol;
    }

    @Override
    public String toString() {
        return "HuobiTicker{" +
                "last=" + last +
                ", buy=" + buy +
                ", sell=" + sell +
                ", high=" + high +
                ", low=" + low +
                ", vol=" + vol +
                '}';
    }
}

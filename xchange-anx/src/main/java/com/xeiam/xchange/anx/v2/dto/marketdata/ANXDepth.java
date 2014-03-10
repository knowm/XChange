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
package com.xeiam.xchange.anx.v2.dto.marketdata;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data object representing depth from Mt Gox
 */
public final class ANXDepth {

  private final List<ANXOrder> asks;
  private final List<ANXOrder> bids;
  private final FilterPrice filterMinPrice;
  private final FilterPrice filterMaxPrice;
  private final Long microTime;

  /**
   * Constructor
   * 
   * @param asks
   * @param bids
   */
  public ANXDepth(@JsonProperty("now") Long microTime, @JsonProperty("asks") List<ANXOrder> asks, @JsonProperty("bids") List<ANXOrder> bids,
                  @JsonProperty("filter_min_price") FilterPrice filterMinPrice, @JsonProperty("filter_max_price") FilterPrice filterMaxPrice) {

    this.asks = asks;
    this.bids = bids;
    this.filterMinPrice = filterMinPrice;
    this.filterMaxPrice = filterMaxPrice;
    this.microTime = microTime;
  }

  public List<ANXOrder> getAsks() {

    return asks;
  }

  public List<ANXOrder> getBids() {

    return bids;
  }

  public FilterPrice getFilterMinPrice() {

    return filterMinPrice;
  }

  public FilterPrice getFilterMaxPrice() {

    return filterMaxPrice;
  }

  public Long getMicroTime() {

    return microTime;
  }

  @Override
  public String toString() {

    return "ANXDepth [asks=" + asks.toString() + ", bids=" + bids.toString() + "]";
  }

  public static class FilterPrice {

    private final BigDecimal value;
    private final long valueInt;
    private final String currency;

    /**
     * Constructor
     * 
     * @param value
     * @param valueInt
     * @param currency
     */
    public FilterPrice(@JsonProperty("value") BigDecimal value, @JsonProperty("value_int") long valueInt, @JsonProperty("currency") String currency) {

      this.value = value;
      this.valueInt = valueInt;
      this.currency = currency;
    }

    public BigDecimal getValue() {

      return value;
    }

    public long getValueInt() {

      return valueInt;
    }

    public String getCurrency() {

      return currency;
    }
  }

}

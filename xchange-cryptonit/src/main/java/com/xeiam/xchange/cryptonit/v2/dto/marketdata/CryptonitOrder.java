/**
 * The MIT License
 * Copyright (c) 2012 Xeiam LLC http://xeiam.com
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
package com.xeiam.xchange.cryptonit.v2.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * <p>
 * Data object representing a Trade from Cryptonit
 * </p>
 */

public final class CryptonitOrder {

  private final String type;
  private final String bid_currency;
  private final String ask_currency;
  private final BigDecimal bid_amount;
  private final BigDecimal ask_amount;
  private final BigDecimal ask_rate;
  private final BigDecimal bid_rate;
  private final long created;
  private final long filled;

  /**
   * @param type
   * @param bid_currency
   * @param ask_currency
   * @param bid_amount
   * @param ask_amount
   * @param ask_rate
   * @param bid_rate
   * @param created
   * @param filled
   */
  public CryptonitOrder(@JsonProperty("type") String type, @JsonProperty("bid_currency") String bid_currency, @JsonProperty("ask_currency") String ask_currency,
      @JsonProperty("bid_amount") BigDecimal bid_amount, @JsonProperty("ask_amount") BigDecimal ask_amount, @JsonProperty("ask_rate") BigDecimal ask_rate,
      @JsonProperty("bid_rate") BigDecimal bid_rate, @JsonProperty("created") long created, @JsonProperty("filled") long filled) {

    this.type = type;
    this.bid_currency = bid_currency;
    this.ask_currency = ask_currency;
    this.bid_amount = bid_amount;
    this.ask_amount = ask_amount;
    this.ask_rate = ask_rate;
    this.bid_rate = bid_rate;
    this.created = created;
    this.filled = filled;
  }

  public String getType() {

    return type;
  }

  public String getBidCurrency() {

    return bid_currency;
  }

  public String getAskCurrency() {

    return ask_currency;
  }

  public BigDecimal getBidAmount() {

    return bid_amount;
  }

  public BigDecimal getAskAmount() {

    return ask_amount;
  }

  public BigDecimal getAskRate() {

    return ask_rate;
  }

  public long getCreated() {

    return created;
  }

  public long getFilled() {

    return filled;
  }

  public BigDecimal getBidRate() {

    return bid_rate;
  }

  @Override
  public String toString() {

    return "CryptonitOrder [type=" + type + ", bid_currency=" + bid_currency + ", ask_currency=" + ask_currency + ", bid_amount=" + bid_amount + ", ask_amount=" + ask_amount + ", ask_rate="
        + ask_rate + ", created=" + created + ", filled=" + filled + "]";

  }

}

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

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author timmolter
 */
public final class BitcoinCentralTrade {

  private final String uuid;
  private final BigDecimal tradedBtc;
  private final BigDecimal tradedCurrency;
  private final String createdAt;
  private final long createdAtInt;
  private final String currency;
  private final BigDecimal price;

  /**
   * Constructor
   * 
   * @param uuid
   * @param tradedBtc
   * @param tradedCurrency
   * @param createdAt
   * @param currency
   * @param price
   */
  public BitcoinCentralTrade(@JsonProperty("uuid") String uuid, @JsonProperty("traded_btc") BigDecimal tradedBtc, @JsonProperty("traded_currency") BigDecimal tradedCurrency,
      @JsonProperty("created_at") String createdAt, @JsonProperty("created_at_int") long createdAtInt, @JsonProperty("currency") String currency, @JsonProperty("price") BigDecimal price) {

    this.uuid = uuid;
    this.tradedBtc = tradedBtc;
    this.tradedCurrency = tradedCurrency;
    this.createdAt = createdAt;
    this.createdAtInt = createdAtInt;
    this.currency = currency;
    this.price = price;
  }

  public String getUuid() {

    return uuid;
  }

  public BigDecimal getTradedBtc() {

    return tradedBtc;
  }

  public BigDecimal getTradedCurrency() {

    return tradedCurrency;
  }

  public String getCreatedAt() {

    return createdAt;
  }
  
  public long getCreatedAtInt() {

	    return createdAtInt;
  }

  public String getCurrency() {

    return currency;
  }

  public BigDecimal getPrice() {

    return price;
  }

}

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
public final class BitcoinCentralTrade {

  private final String category;
  private final String createdAt;
  private final String currency;
  private final BigDecimal id;
  private final BigDecimal ppc;
  private final BigDecimal tradedBtc;
  private final BigDecimal tradedCurrency;

  /**
   * Constructor
   * 
   * @param category
   * @param createdAt
   * @param currency
   * @param id
   * @param ppc
   * @param tradedBtc
   * @param tradedCurrency
   */
  public BitcoinCentralTrade(@JsonProperty("category") String category, @JsonProperty("created_at") String createdAt, @JsonProperty("currency") String currency, @JsonProperty("id") BigDecimal id,
      @JsonProperty("ppc") BigDecimal ppc, @JsonProperty("traded_btc") BigDecimal tradedBtc, @JsonProperty("traded_currency") BigDecimal tradedCurrency) {

    this.category = category;
    this.createdAt = createdAt;
    this.currency = currency;
    this.id = id;
    this.ppc = ppc;
    this.tradedBtc = tradedBtc;
    this.tradedCurrency = tradedCurrency;
  }

  public String getCategory() {

    return category;
  }

  public String getCreatedAt() {

    return createdAt;
  }

  public String getCurrency() {

    return currency;
  }

  public BigDecimal getId() {

    return id;
  }

  public BigDecimal getPpc() {

    return ppc;
  }

  public BigDecimal getTradedBtc() {

    return tradedBtc;
  }

  public BigDecimal getTradedCurrency() {

    return tradedCurrency;
  }

  @Override
  public String toString() {

    return "BitcoinCentralTrades [category=" + category + ", createdAt=" + createdAt + ", currency=" + currency + ", id=" + id + ", ppc=" + ppc + ", tradedBtc=" + tradedBtc + ", tradedCurrency="
        + tradedCurrency + "]";
  }

}

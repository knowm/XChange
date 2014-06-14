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
package com.xeiam.xchange.btcchina.dto.trade;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BTCChinaTransaction {

  private final long id;
  private final String type;
  private final BigDecimal btcAmount;
  private final BigDecimal ltcAmount;
  private final BigDecimal cnyAmount;
  private final long date;

  public BTCChinaTransaction(@JsonProperty("id") long id, @JsonProperty("type") String type, @JsonProperty("btc_amount") BigDecimal btcAmount, @JsonProperty("ltc_amount") BigDecimal ltcAmount,
      @JsonProperty("cny_amount") BigDecimal cnyAmount, @JsonProperty("date") long date) {

    this.id = id;
    this.type = type;
    this.btcAmount = btcAmount;
    this.ltcAmount = ltcAmount;
    this.cnyAmount = cnyAmount;
    this.date = date;
  }

  public long getId() {

    return id;
  }

  public String getType() {

    return type;
  }

  public BigDecimal getBtcAmount() {

    return btcAmount;
  }

  public BigDecimal getCnyAmount() {

    return cnyAmount;
  }

  public BigDecimal getLtcAmount() {

    return ltcAmount;
  }

  public long getDate() {

    return date;
  }
}

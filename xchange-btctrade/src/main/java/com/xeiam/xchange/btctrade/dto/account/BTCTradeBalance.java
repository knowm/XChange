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
package com.xeiam.xchange.btctrade.dto.account;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.btctrade.dto.BTCTradeResult;

public class BTCTradeBalance extends BTCTradeResult {

  private final Long uid;
  private final Integer nameauth;
  private final String moflag;
  private final BigDecimal btcBalance;
  private final BigDecimal btcReserved;
  private final BigDecimal ltcBalance;
  private final BigDecimal ltcReserved;
  private final BigDecimal dogeBalance;
  private final BigDecimal dogeReserved;
  private final BigDecimal ybcBalance;
  private final BigDecimal ybcReserved;
  private final BigDecimal cnyBalance;
  private final BigDecimal cnyReserved;

  public BTCTradeBalance(@JsonProperty("result") Boolean result, @JsonProperty("message") String message, @JsonProperty("uid") Long uid, @JsonProperty("nameauth") Integer nameauth,
      @JsonProperty("moflag") String moflag, @JsonProperty("btc_balance") BigDecimal btcBalance, @JsonProperty("btc_reserved") BigDecimal btcReserved,
      @JsonProperty("ltc_balance") BigDecimal ltcBalance, @JsonProperty("ltc_reserved") BigDecimal ltcReserved, @JsonProperty("doge_balance") BigDecimal dogeBalance,
      @JsonProperty("doge_reserved") BigDecimal dogeReserved, @JsonProperty("ybc_balance") BigDecimal ybcBalance, @JsonProperty("ybc_reserved") BigDecimal ybcReserved,
      @JsonProperty("cny_balance") BigDecimal cnyBalance, @JsonProperty("cny_reserved") BigDecimal cnyReserved) {

    super(result, message);
    this.uid = uid;
    this.nameauth = nameauth;
    this.moflag = moflag;
    this.btcBalance = btcBalance;
    this.btcReserved = btcReserved;
    this.ltcBalance = ltcBalance;
    this.ltcReserved = ltcReserved;
    this.dogeBalance = dogeBalance;
    this.dogeReserved = dogeReserved;
    this.ybcBalance = ybcBalance;
    this.ybcReserved = ybcReserved;
    this.cnyBalance = cnyBalance;
    this.cnyReserved = cnyReserved;
  }

  public Long getUid() {

    return uid;
  }

  public Integer getNameauth() {

    return nameauth;
  }

  public String getMoflag() {

    return moflag;
  }

  public BigDecimal getBtcBalance() {

    return btcBalance;
  }

  public BigDecimal getBtcReserved() {

    return btcReserved;
  }

  public BigDecimal getLtcBalance() {

    return ltcBalance;
  }

  public BigDecimal getLtcReserved() {

    return ltcReserved;
  }

  public BigDecimal getDogeBalance() {

    return dogeBalance;
  }

  public BigDecimal getDogeReserved() {

    return dogeReserved;
  }

  public BigDecimal getYbcBalance() {

    return ybcBalance;
  }

  public BigDecimal getYbcReserved() {

    return ybcReserved;
  }

  public BigDecimal getCnyBalance() {

    return cnyBalance;
  }

  public BigDecimal getCnyReserved() {

    return cnyReserved;
  }

}

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
package com.xeiam.xchange.cryptsy.dto.account;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.cryptsy.CryptsyUtils;

/**
 * @author ObsessiveOrange
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class CryptsyAccountInfo {

  private final int openOrders;
  private final Date timeStamp;
  private final Map<String, BigDecimal> availableFunds;
  private final Map<String, BigDecimal> availableFundsBTC;

  @JsonCreator
  public CryptsyAccountInfo(@JsonProperty("openordercount") Integer openordercount, @JsonProperty("serverdatetime") String timeStamp,
      @JsonProperty("balances_available") Map<String, BigDecimal> availableFunds, @JsonProperty("balances_available_btc") Map<String, BigDecimal> availableFundsBTC) throws ParseException {

    this.openOrders = openordercount;
    this.timeStamp = timeStamp == null ? null : CryptsyUtils.convertDateTime(timeStamp);
    this.availableFunds = availableFunds;
    this.availableFundsBTC = availableFundsBTC;
  }

  public int getOpenOrders() {

    return openOrders;
  }

  public Date getTimeStamp() {

    return timeStamp;
  }

  public Map<String, BigDecimal> getAvailableFunds() {

    return availableFunds;
  }

  public Map<String, BigDecimal> getAvailableFundsBTC() {

    return availableFundsBTC;
  }

  @Override
  public String toString() {

    return "CryptsyAccountInfo[" + "availableFunds='" + availableFunds + "', Available Funds in BTC='" + availableFundsBTC + "',Open Orders='" + openOrders + "',TimeStamp='" + timeStamp + "']";
  }
}

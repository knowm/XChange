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
package com.xeiam.xchange.coinfloor.dto.streaming.trade;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.coinfloor.CoinfloorUtils;
import com.xeiam.xchange.coinfloor.CoinfloorUtils.CoinfloorCurrency;

/**
 * @author obsessiveOrange
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class CoinfloorEstimateMarketOrder {

  private final int tag;
  private final int errorCode;
  private final BigDecimal baseQty;
  private final BigDecimal counterQty;

  public CoinfloorEstimateMarketOrder(@JsonProperty("tag") int tag, @JsonProperty("errorCode") int errorCode, @JsonProperty("quantity") int baseQty, @JsonProperty("total") int counterQty) {

    this.tag = tag;
    this.errorCode = errorCode;
    this.baseQty = CoinfloorUtils.scaleToBigDecimal(CoinfloorCurrency.BTC, baseQty);
    this.counterQty = CoinfloorUtils.scaleToBigDecimal(CoinfloorCurrency.GBP, counterQty);
  }

  public int getTag() {

    return tag;
  }

  public int getErrorCode() {

    return errorCode;
  }

  public BigDecimal getBaseQty() {

    return baseQty;
  }

  public BigDecimal getCounterQty() {

    return counterQty;
  }

  @Override
  public String toString() {

    return "CoinfloorEstimateMarketOrderReturn{tag='" + tag + "', errorcode='" + errorCode + "', baseQty='" + baseQty + "', counterQty='" + counterQty + "'}";
  }
}

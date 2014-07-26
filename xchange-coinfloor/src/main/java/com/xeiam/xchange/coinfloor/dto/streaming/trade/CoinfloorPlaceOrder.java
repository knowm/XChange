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
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.coinfloor.CoinfloorUtils;
import com.xeiam.xchange.coinfloor.CoinfloorUtils.CoinfloorCurrency;

/**
 * @author obsessiveOrange
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class CoinfloorPlaceOrder {

  private final int tag;
  private final int errorCode;
  private final int id;
  private final long time;
  private final BigDecimal remaining;

  public CoinfloorPlaceOrder(@JsonProperty("tag") int tag, @JsonProperty("error_code") int errorCode, @JsonProperty("id") int id, @JsonProperty("time") long time,
      @JsonProperty("remaining") int remaining) {

    this.tag = tag;
    this.errorCode = errorCode;
    this.id = id;
    this.time = time / 1000;
    this.remaining = CoinfloorUtils.scaleToBigDecimal(CoinfloorCurrency.BTC, remaining);
  }

  public int getTag() {

    return tag;
  }

  public int getErrorCode() {

    return errorCode;
  }

  public int getId() {

    return id;
  }

  public long getTime() {

    return time;
  }

  public BigDecimal getRemaining() {

    return remaining;
  }

  @Override
  public String toString() {

    return "CoinfloorPlaceOrderReturn{tag='" + tag + "', errorCode='" + errorCode + "', id='" + id + "', Time='" + new Date(time) + "', remaining='" + remaining + "'}";
  }
}

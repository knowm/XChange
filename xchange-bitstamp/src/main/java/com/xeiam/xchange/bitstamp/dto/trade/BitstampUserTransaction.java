/**
 * Copyright (C) 2013 Matija Mazi
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
package com.xeiam.xchange.bitstamp.dto.trade;

import java.math.BigDecimal;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author Matija Mazi
 */
public final class BitstampUserTransaction {

  private final String datetime;
  private final long id;
  private final int type;
  private final BigDecimal usd;
  private final BigDecimal btc;
  private final BigDecimal fee;

  /**
   * Constructor
   * 
   * @param datetime
   * @param id
   * @param type
   * @param usd
   * @param btc
   * @param fee
   */
  public BitstampUserTransaction(@JsonProperty("datetime") String datetime, @JsonProperty("id") long id, @JsonProperty("type") int type, @JsonProperty("usd") BigDecimal usd,
      @JsonProperty("btc") BigDecimal btc, @JsonProperty("fee") BigDecimal fee) {

    this.datetime = datetime;
    this.id = id;
    this.type = type;
    this.usd = usd;
    this.btc = btc;
    this.fee = fee;
  }

  public String getDatetime() {

    return datetime;
  }

  public long getId() {

    return id;
  }

  /** (0 - deposit; 1 - withdrawal; 2 - market trade) */
  public int getType() {

    return type;
  }

  public boolean isDeposit() {

    return type == 0;
  }

  public boolean isWithdrawal() {

    return type == 1;
  }

  public boolean isMarketTrade() {

    return type == 2;
  }

  public BigDecimal getUsd() {

    return usd;
  }

  public BigDecimal getBtc() {

    return btc;
  }

  public BigDecimal getFee() {

    return fee;
  }

  @Override
  public String toString() {

    return String.format("UserTransaction{datetime=%s, id=%d, type=%d, usd=%s, btc=%s, fee=%s}", datetime, id, type, usd, btc, fee);
  }
}

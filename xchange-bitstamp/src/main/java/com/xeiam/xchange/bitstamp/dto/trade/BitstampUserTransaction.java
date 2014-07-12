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
package com.xeiam.xchange.bitstamp.dto.trade;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.xeiam.xchange.bitstamp.util.BitstampTransactionTypeDeserializer;

/**
 * @author Matija Mazi
 */
public final class BitstampUserTransaction {

  private final String datetime;
  private final long id;
  private final long order_id;
  private final TransactionType type;
  /** USD amount, negative -> BID, positive -> ASK */
  private final BigDecimal usd;
  private final BigDecimal btc;
  /** price, has the reciprocal sign compared to 'usd' value */
  private final BigDecimal btc_usd;
  private final BigDecimal fee;

  /**
   * Constructor
   * 
   * @param datetime
   * @param id
   * @param order_id
   * @param type
   * @param usd
   * @param btc
   * @param btc_usd
   * @param fee
   */
  public BitstampUserTransaction(@JsonProperty("datetime") String datetime, @JsonProperty("id") long id, @JsonProperty("order_id") long order_id, @JsonProperty("type") @JsonDeserialize(
      using = BitstampTransactionTypeDeserializer.class) TransactionType type, @JsonProperty("usd") BigDecimal usd, @JsonProperty("btc") BigDecimal btc, @JsonProperty("btc_usd") BigDecimal btc_usd,
      @JsonProperty("fee") BigDecimal fee) {

    this.datetime = datetime;
    this.id = id;
    this.order_id = order_id;
    this.type = type;
    this.usd = usd;
    this.btc = btc;
    this.btc_usd = btc_usd;
    this.fee = fee;
  }

  public String getDatetime() {

    return datetime;
  }

  public long getId() {

    return id;
  }

  public long getOrderId() {

    return order_id;
  }

  public TransactionType getType() {

    return type;
  }

  public boolean isDeposit() {

    return type == TransactionType.deposit;
  }

  public boolean isWithdrawal() {

    return type == TransactionType.withdrawal;
  }

  public boolean isMarketTrade() {

    return type == TransactionType.trade;
  }

  public BigDecimal getUsd() {

    return usd;
  }

  public BigDecimal getBtc() {

    return btc;
  }

  public BigDecimal getPrice() {

    return btc_usd;
  }

  public BigDecimal getFee() {

    return fee;
  }

  @Override
  public String toString() {

    return String.format("UserTransaction{datetime=%s, id=%d, type=%s, usd=%s, btc=%s, fee=%s}", datetime, id, type, usd, btc, fee);
  }

  public enum TransactionType {
    deposit, withdrawal, trade
  }
}

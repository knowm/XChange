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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.cryptsy.CryptsyUtils;

/**
 * @author ObsessiveOrange
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class CryptsyTxn {

  private final String currency;
  private final CryptsyTxnType type;
  private final Date timeStamp;
  private final String address;
  private final BigDecimal amount;
  private final BigDecimal fee;
  private final String txnId;

  /**
   * Constructor
   * 
   * @param transactionCount The number of transactions
   * @param openOrders The open orders
   * @param serverTime The server time (Unix time)
   * @param rights The rights
   * @param funds The funds
   * @throws ParseException
   */
  public CryptsyTxn(@JsonProperty("currency") String currency, @JsonProperty("type") CryptsyTxnType type, @JsonProperty("datetime") String timeStamp, @JsonProperty("address") String address,
      @JsonProperty("amount") BigDecimal amount, @JsonProperty("fee") BigDecimal fee, @JsonProperty("trxid") String txnId) throws ParseException {

    this.currency = currency;
    this.type = type;
    this.timeStamp = timeStamp == null ? null : CryptsyUtils.convertDateTime(timeStamp);
    this.address = address;
    this.amount = amount;
    this.fee = fee;
    this.txnId = txnId;
  }

  public String getCurrency() {

    return currency;
  }

  public CryptsyTxnType getType() {

    return type;
  }

  public Date getTimestamp() {

    return timeStamp;
  }

  public String getAddress() {

    return address;
  }

  public BigDecimal getAmount() {

    return amount;
  }

  public BigDecimal getFee() {

    return fee;
  }

  public String getTransactionId() {

    return txnId;
  }

  @Override
  public String toString() {

    return "CryptsyTransactionHistory[" + "Currency='" + currency + "', Type='" + type + "',Timestamp='" + timeStamp + "',Address='" + address + "',Amount='" + amount + "',Fee='" + fee
        + "',Transaction ID='" + txnId + "']";
  }

  public static enum CryptsyTxnType {
    Deposit, Withdrawal
  }
}

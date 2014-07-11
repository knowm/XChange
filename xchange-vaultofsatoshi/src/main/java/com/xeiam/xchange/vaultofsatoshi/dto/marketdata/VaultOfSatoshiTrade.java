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
package com.xeiam.xchange.vaultofsatoshi.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * <p>
 * Data object representing a Trade from VaultOfSatoshi
 * </p>
 */

public final class VaultOfSatoshiTrade {

  private final long transaction_date;
  private final long journalId;
  private final long transactionId;
  private final int inverseTrade;
  private final VosCurrency units_traded;
  private final VosCurrency price;
  private final VosCurrency total;

  public VaultOfSatoshiTrade(@JsonProperty("transaction_date") long transaction_date, @JsonProperty("journal_id") long journalId, @JsonProperty("transaction_id") long transactionId,
      @JsonProperty("inverse_trade") int inverseTrade, @JsonProperty("units_traded") VosCurrency units_traded, @JsonProperty("price") VosCurrency price, @JsonProperty("total") VosCurrency total) {

    this.transaction_date = transaction_date;
    this.journalId = journalId;
    this.transactionId = transactionId;
    this.inverseTrade = inverseTrade;
    this.units_traded = units_traded;
    this.total = total;
    this.price = price;
  }

  public VosCurrency getPrice() {

    return price;
  }

  public VosCurrency getTotal() {

    return total;
  }

  public VosCurrency getUnitsTraded() {

    return units_traded;
  }

  public long getTimestamp() {

    return transaction_date;
  }

  public long getJournalId() {

    return journalId;
  }

  public long getTransactionId() {

    return transactionId;
  }

  public int getInverseTrade() {

    return inverseTrade;
  }

  @Override
  public String toString() {

    return "VaultOfSatoshiTrade [transaction_date=" + transaction_date + ", journalId=" + journalId + ", transactionId=" + transactionId + ", inverseTrade=" + inverseTrade + ", units_traded="
        + units_traded + ", price=" + price + ", total=" + total + "]";
  }

}

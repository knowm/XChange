/**
 * Copyright (C) 2012 - 2013 Xeiam LLC http://xeiam.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.xeiam.xchange.mtgox.v2.dto.account.polling;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.mtgox.v2.dto.MtGoxValue;

/**
 * Data object representing a MtGox Wallet History Entry
 */
public final class MtGoxWalletHistoryEntry {

  private final int index;
  private final String date;
  private final String type;
  private final MtGoxValue value;
  private final MtGoxValue balance;

  /**
   * Constructor
   *
   * @param index
   * @param date
   * @param type
   * @param value
   * @param balance
   *
   */
  public MtGoxWalletHistoryEntry(@JsonProperty("Index") int index, @JsonProperty("Date") String date, @JsonProperty("Type") String type,
          @JsonProperty("Value") MtGoxValue value, @JsonProperty("Balance") MtGoxValue balance) {

    this.index = index;
    this.date = date;
    this.type = type;
    this.value = value;
    this.balance = balance;
  }

  public int getIndex() {
    return index;
  }

  public String getDate() {
    return date;
  }

  public String getType() {
    return type;
  }

  public MtGoxValue getValue() {
    return value;
  }

  public MtGoxValue getBalance() {
    return balance;
  }

  @Override
  public String toString() {
    return "MtGoxWalletHistory{" + "index=" + index + ", date=" + date + ", type=" + type + ", value=" + value + ", balance=" + balance + '}';
  }
}

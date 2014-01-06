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
  private final String info;
  private final String[] link;
  private final MtGoxWalletHistoryEntryTrade trade;

  /**
   * Constructor
   * 
   * @param index
   * @param date
   * @param type
   * @param value
   * @param balance
   * @param link
   * @param info
   * @param trade
   */
  public MtGoxWalletHistoryEntry(@JsonProperty("Index") int index, @JsonProperty("Date") String date, @JsonProperty("Type") String type, @JsonProperty("Info") String info,
      @JsonProperty("Link") String[] link, @JsonProperty("Value") MtGoxValue value, @JsonProperty("Balance") MtGoxValue balance, @JsonProperty("Trade") MtGoxWalletHistoryEntryTrade trade) {

    this.index = index;
    this.date = date;
    this.type = type;
    this.info = info;
    this.link = link;
    this.value = value;
    this.balance = balance;
    this.trade = trade;
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

  public String getInfo() {

    return info;
  }

  public String[] getLink() {

    return link;
  }

  public MtGoxWalletHistoryEntryTrade getTrade() {

    return trade;
  }

  @Override
  public String toString() {

    return "MtGoxWalletHistoryEntry{" + "index=" + index + ", date=" + date + ", type=" + type + ", value=" + value + ", balance=" + balance + ", info=" + info + ", link=" + link + ", trade=" + trade
        + '}';
  }

  public static class MtGoxWalletHistoryEntryTrade {

    private final String oid;
    private final String tid;
    private final String app;
    private final String properties;
    private final MtGoxValue amount;

    public MtGoxWalletHistoryEntryTrade(@JsonProperty("oid") String oid, @JsonProperty("tid") String tid, @JsonProperty("app") String app, @JsonProperty("Properties") String properties,
        @JsonProperty("Amount") MtGoxValue amount) {

      this.oid = oid;
      this.tid = tid;
      this.app = app;
      this.properties = properties;
      this.amount = amount;
    }

    public MtGoxValue getAmount() {

      return amount;
    }

    public String getProperties() {

      return properties;
    }

    public String getApp() {

      return app;
    }

    public String getOid() {

      return oid;
    }

    public String getTid() {

      return tid;
    }

    @Override
    public String toString() {

      return "MtGoxWalletHistoryEntryTrade{" + "oid=" + oid + ", tid=" + tid + ", app=" + app + ", properties=" + properties + ", amount=" + amount + '}';
    }

  }
}

/**
 * Copyright (C) 2012 Xeiam LLC http://xeiam.com
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
package com.xeiam.xchange.mtgox.v1.service.marketdata;

import java.util.List;

/**
 * Data object representing trades from Mt Gox
 */
public class MtGoxTrades {

  List<MtGoxTrade> trades;

  public List<MtGoxTrade> getTrades() {
    return trades;
  }

  public void setTrades(List<MtGoxTrade> trades) {
    this.trades = trades;
  }

  /**
   * Data object representing a trade from Mt Gox
   */
  public class MtGoxTrade {

    long date;
    double price;
    double amount;
    long price_int;
    long amount_int;
    long tid;
    String price_currency;
    String item;
    String trade_type;
    String primary;
    String properties;

    public long getDate() {
      return date;
    }

    public void setDate(long date) {
      this.date = date;
    }

    public double getPrice() {
      return price;
    }

    public void setPrice(double price) {
      this.price = price;
    }

    public double getAmount() {
      return amount;
    }

    public void setAmount(double amount) {
      this.amount = amount;
    }

    public long getPrice_int() {
      return price_int;
    }

    public void setPrice_int(long price_int) {
      this.price_int = price_int;
    }

    public long getAmount_int() {
      return amount_int;
    }

    public void setAmount_int(long amount_int) {
      this.amount_int = amount_int;
    }

    public long getTid() {
      return tid;
    }

    public void setTid(long tid) {
      this.tid = tid;
    }

    public String getPrice_currency() {
      return price_currency;
    }

    public void setPrice_currency(String price_currency) {
      this.price_currency = price_currency;
    }

    public String getItem() {
      return item;
    }

    public void setItem(String item) {
      this.item = item;
    }

    public String getTrade_type() {
      return trade_type;
    }

    public void setTrade_type(String trade_type) {
      this.trade_type = trade_type;
    }

    public String getPrimary() {
      return primary;
    }

    public void setPrimary(String primary) {
      this.primary = primary;
    }

    public String getProperties() {
      return properties;
    }

    public void setProperties(String properties) {
      this.properties = properties;
    }
  }

}

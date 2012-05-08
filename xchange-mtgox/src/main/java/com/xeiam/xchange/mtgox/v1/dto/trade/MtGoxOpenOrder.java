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
package com.xeiam.xchange.mtgox.v1.dto.trade;

import com.xeiam.xchange.mtgox.v1.dto.MtGoxValue;

/**
 * Data object representing Open Orders from Mt Gox
 */
public class MtGoxOpenOrder {

  private String oid;
  private String currency;
  private String item;
  private String type;
  private MtGoxValue amount = new MtGoxValue();
  private MtGoxValue invalid_amount = new MtGoxValue();
  private MtGoxValue price = new MtGoxValue();
  private String status;
  private long date;
  private long priority;

  public String getOid() {
    return oid;
  }

  public void setOid(String oid) {
    this.oid = oid;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public String getItem() {
    return item;
  }

  public void setItem(String item) {
    this.item = item;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public MtGoxValue getAmount() {
    return amount;
  }

  public void setAmount(MtGoxValue amount) {
    this.amount = amount;
  }

  public MtGoxValue getInvalid_amount() {
    return invalid_amount;
  }

  public void setInvalid_amount(MtGoxValue invalid_amount) {
    this.invalid_amount = invalid_amount;
  }

  public MtGoxValue getPrice() {
    return price;
  }

  public void setPrice(MtGoxValue price) {
    this.price = price;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public long getDate() {
    return date;
  }

  public void setDate(long date) {
    this.date = date;
  }

  public long getPriority() {
    return priority;
  }

  public void setPriority(long priority) {
    this.priority = priority;
  }

  @Override
  public String toString() {
    return "MtGoxOpenOrder [oid=" + oid + ", currency=" + currency + ", item=" + item + ", type=" + type + ", amount=" + amount + ", invalid_amount=" + invalid_amount + ", price=" + price + ", status=" + status
        + ", date=" + date + ", priority=" + priority + "]";
  }

}

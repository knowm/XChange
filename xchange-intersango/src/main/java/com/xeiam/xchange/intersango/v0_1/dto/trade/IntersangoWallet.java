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
package com.xeiam.xchange.intersango.v0_1.dto.trade;

/**
 * <p>
 * Data object representing Account Info from Intersango
 * </p>
 */
public class IntersangoWallet {

  private long id;
  private String balance;
  private int currency_id;
  private String currency_abbreviation;
  private String account_trade_fee_percentage;
  private String reference_code;
  private String liabilities;
  private String bitcoin_address;

  public String getAccount_trade_fee_percentage() {
    return account_trade_fee_percentage;
  }

  public void setAccount_trade_fee_percentage(String account_trade_fee_percentage) {
    this.account_trade_fee_percentage = account_trade_fee_percentage;
  }

  public String getBalance() {
    return balance;
  }

  public void setBalance(String balance) {
    this.balance = balance;
  }

  public String getCurrency_abbreviation() {
    return currency_abbreviation;
  }

  public void setCurrency_abbreviation(String currency_abbreviation) {
    this.currency_abbreviation = currency_abbreviation;
  }

  public int getCurrency_id() {
    return currency_id;
  }

  public void setCurrency_id(int currency_id) {
    this.currency_id = currency_id;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getLiabilities() {
    return liabilities;
  }

  public void setLiabilities(String liabilities) {
    this.liabilities = liabilities;
  }

  public String getReference_code() {
    return reference_code;
  }

  public void setReference_code(String reference_code) {
    this.reference_code = reference_code;
  }

  public String getBitcoin_address() {
    return bitcoin_address;
  }

  public void setBitcoin_address(String bitcoin_address) {
    this.bitcoin_address = bitcoin_address;
  }
}

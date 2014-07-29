/**
 * The MIT License
 * Copyright (c) 2012 Xeiam LLC http://xeiam.com
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
package com.xeiam.xchange.bittrex.v1.dto.account;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BittrexBalance {

  private BigDecimal available;
  private BigDecimal balance;
  private String cryptoAddress;
  private String currency;
  private BigDecimal pending;
  private boolean requested;
  private String uuid;

  public BittrexBalance(@JsonProperty("Available") BigDecimal available, @JsonProperty("Balance") BigDecimal balance, @JsonProperty("CryptoAddress") String cryptoAddress,
      @JsonProperty("Currency") String currency, @JsonProperty("Pending") BigDecimal pending, @JsonProperty("Requested") boolean requested, @JsonProperty("Uuid") String uuid) {

    super();
    this.available = available;
    this.balance = balance;
    this.cryptoAddress = cryptoAddress;
    this.currency = currency;
    this.pending = pending;
    this.requested = requested;
    this.uuid = uuid;
  }

  public BigDecimal getAvailable() {

    return available;
  }

  public void setAvailable(BigDecimal available) {

    this.available = available;
  }

  public BigDecimal getBalance() {

    return balance;
  }

  public void setBalance(BigDecimal balance) {

    this.balance = balance;
  }

  public String getCryptoAddress() {

    return cryptoAddress;
  }

  public void setCryptoAddress(String cryptoAddress) {

    this.cryptoAddress = cryptoAddress;
  }

  public String getCurrency() {

    return currency;
  }

  public void setCurrency(String currency) {

    this.currency = currency;
  }

  public BigDecimal getPending() {

    return pending;
  }

  public void setPending(BigDecimal pending) {

    this.pending = pending;
  }

  public boolean isRequested() {

    return requested;
  }

  public void setRequested(boolean requested) {

    this.requested = requested;
  }

  public String getUuid() {

    return uuid;
  }

  public void setUuid(String uuid) {

    this.uuid = uuid;
  }

  @Override
  public String toString() {

    return "Bittrexbalance [available=" + available + ", balance=" + balance + ", cryptoAddress=" + cryptoAddress + ", currency=" + currency + ", pending=" + pending + ", requested=" + requested
        + ", uuid=" + uuid + "]";
  }

}
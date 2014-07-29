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
package com.xeiam.xchange.bitfinex.v1.dto.trade;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BitfinexNewOfferRequest {

  @JsonProperty("request")
  protected String request;
  
  @JsonProperty("nonce")
  protected String nonce;
  
  @JsonProperty("currency")
  protected String currency;
  
  @JsonProperty("amount")
  protected BigDecimal amount;
  
  @JsonProperty("rate")
  protected BigDecimal rate;
  
  @JsonProperty("period")
  protected int period;
  
  @JsonProperty("direction")
  protected String direction;
  
  public BitfinexNewOfferRequest(String nonce, String currency, BigDecimal amount, BigDecimal rate, int period, String direction) {
    
    this.request = "/v1/offer/new";
    this.nonce = nonce;
    this.currency = currency;
    this.amount = amount;
    this.rate = rate;
    this.period = period;
    this.direction = direction;
  }

  public String getRequest() {
    return request;
  }

  public void setRequest(String request) {
    this.request = request;
  }

  public String getNonce() {
    return nonce;
  }

  public void setNonce(String nonce) {
    this.nonce = nonce;
  }

  public String getCurrency() {
    return currency;
  }

  public String getAmount() {
    return amount.toPlainString();
  }

  public String getRate() {
    return rate.toPlainString();
  }

  public int getPeriod() {
    return period;
  }

  public String getDirection() {
    return direction;
  }
  
}

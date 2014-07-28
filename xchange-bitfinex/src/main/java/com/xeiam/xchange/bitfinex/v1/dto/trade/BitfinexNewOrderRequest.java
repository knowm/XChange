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

public final class BitfinexNewOrderRequest {

  @JsonProperty("request")
  protected String request;

  @JsonProperty("nonce")
  protected String nonce;

  @JsonProperty("symbol")
  protected String symbol;

  @JsonProperty("exchange")
  protected String exchange;

  @JsonProperty("side")
  protected String side;

  @JsonProperty("type")
  protected String type;

  @JsonProperty("amount")
  protected BigDecimal amount;

  @JsonProperty("price")
  protected BigDecimal price;

  /**
   * Constructor
   * 
   * @param nonce
   * @param symbol
   * @param amount
   * @param price
   * @param exchange
   * @param side
   * @param type
   * @param is_hidden
   */
  public BitfinexNewOrderRequest(String nonce, String symbol, BigDecimal amount, BigDecimal price, String exchange, String side, String type, boolean is_hidden) {

    this.request = "/v1/order/new";
    this.nonce = nonce;
    this.symbol = symbol;
    this.amount = amount;
    this.price = price;
    this.exchange = exchange;
    this.side = side;
    this.type = type;
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

  public String getSide() {

    return side;
  }

  public String getType() {

    return type;
  }

  public String getSymbol() {

    return symbol;
  }

  public String getAmount() {

    return amount.toPlainString();
  }

  public String getPrice() {

    return price.toPlainString();
  }
}

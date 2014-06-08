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
package com.xeiam.xchange.hitbtc.dto.trade;

import java.math.BigDecimal;

import javax.ws.rs.FormParam;

public class HitbtcNewOrderRequest {

  @FormParam("clientOrderId")
  protected String clientOrderId;

  @FormParam("symbol")
  protected String symbol;

  @FormParam("side")
  protected String side;

  @FormParam("price")
  protected BigDecimal price;

  @FormParam("quantity")
  protected int quantity;

  @FormParam("timeInForce")
  protected String timeInForce;

  public HitbtcNewOrderRequest(String clientOrderId, String symbol, String side, BigDecimal price, int quantity, String timeInForce) {

    super();
    this.clientOrderId = clientOrderId;
    this.symbol = symbol;
    this.side = side;
    this.price = price;
    this.quantity = quantity;
    this.timeInForce = timeInForce;
  }

  public String getClientOrderId() {

    return clientOrderId;
  }

  public String getSymbol() {

    return symbol;
  }

  public String getSide() {

    return side;
  }

  public BigDecimal getPrice() {

    return price;
  }

  public int getQuantity() {

    return quantity;
  }

  public String getTimeInForce() {

    return timeInForce;
  }

}

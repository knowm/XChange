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
package com.xeiam.xchange.itbit.v1.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ItBitPlaceOrderRequest {

  @JsonProperty("side")
  private String side;

  @JsonProperty("type")
  protected String type;

  @JsonProperty("currency")
  protected String baseCurrency;

  @JsonProperty("amount")
  private String amount;

  @JsonProperty("price")
  private String price;

  @JsonProperty("instrument")
  private String instrument;

  public ItBitPlaceOrderRequest(String side, String type, String baseCurrency, String amount, String price, String instrument) {

    super();
    this.side = side;
    this.type = type;
    this.baseCurrency = baseCurrency;
    this.amount = amount;
    this.price = price;
    this.instrument = instrument;
  }

  public String getSide() {

    return side;
  }

  public String getType() {

    return type;
  }

  public String getBaseCurrency() {

    return baseCurrency;
  }

  public String getAmount() {

    return amount;
  }

  public String getPrice() {

    return price;
  }

  public String getInstrument() {

    return instrument;
  }
}

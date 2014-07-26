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
package com.xeiam.xchange.coinfloor.dto.streaming.trade;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.coinfloor.CoinfloorUtils;
import com.xeiam.xchange.coinfloor.CoinfloorUtils.CoinfloorCurrency;

/**
 * @author obsessiveOrange
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class CoinfloorCancelOrder {

  private final int tag;
  private final int errorCode;
  private final CoinfloorCurrency base;
  private final CoinfloorCurrency counter;
  private final BigDecimal quantity;
  private final BigDecimal price;

  public CoinfloorCancelOrder(@JsonProperty("tag") int tag, @JsonProperty("errorCode") int errorCode, @JsonProperty("base") int base, @JsonProperty("counter") int counter,
      @JsonProperty("quantity") int quantity, @JsonProperty("price") int price) {

    this.tag = tag;
    this.errorCode = errorCode;
    this.base = CoinfloorUtils.getCurrency(base);
    this.counter = CoinfloorUtils.getCurrency(counter);
    this.quantity = CoinfloorUtils.scaleToBigDecimal(this.base, quantity);
    this.price = CoinfloorUtils.scalePriceToBigDecimal(this.base, this.counter, price);
  }

  public int getTag() {

    return tag;
  }

  public int getErrorCode() {

    return errorCode;
  }

  public CoinfloorCurrency getBase() {

    return base;
  }

  public CoinfloorCurrency getCounter() {

    return counter;
  }

  public BigDecimal getQuantity() {

    return quantity;
  }

  public BigDecimal getPrice() {

    return price;
  }

  @Override
  public String toString() {

    return "CoinfloorCancelOrderReturn{tag='" + tag + "', errorcode='" + errorCode + "', base='" + base + "', counter='" + counter + "', quantity='" + quantity + "', price='" + price + "'}";
  }
}

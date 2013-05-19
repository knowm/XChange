/**
 * Copyright (C) 2013 Matija Mazi
 * Copyright (C) 2013 Xeiam LLC http://xeiam.com
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
package com.xeiam.xchange.bitstamp.dto.trade;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.bitstamp.BitstampUtils;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Matija Mazi
 */
public final class BitstampOrder {

  private final int id;
  private final String datetime;
  /** 0 - buy (bid); 1 - sell (ask) */
  private final int type;
  private final BigDecimal price;
  private final BigDecimal amount;

  /**
   * Constructor
   * 
   * @param id
   * @param datetime
   * @param type
   * @param price
   * @param amount
   */
  public BitstampOrder(@JsonProperty("id") int id, @JsonProperty("datetime") String datetime, @JsonProperty("type") int type, @JsonProperty("price") BigDecimal price,
      @JsonProperty("amount") BigDecimal amount) {

    this.id = id;
    this.datetime = datetime;
    this.type = type;
    this.price = price;
    this.amount = amount;
  }

  public String getDatetime() {

    return datetime;
  }

  public int getId() {

    return id;
  }

  public int getType() {

    return type;
  }

  public BigDecimal getPrice() {

    return price;
  }

  public BigDecimal getAmount() {

    return amount;
  }

  @JsonIgnore
  public Date getTime() {

    return BitstampUtils.parseDate(getDatetime());
  }

  @Override
  public String toString() {

    return String.format("Order{id=%s, datetime=%s, type=%s, price=%s, amount=%s}", id, datetime, type, price, amount);
  }
}

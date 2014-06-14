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
package com.xeiam.xchange.cryptsy.dto;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.cryptsy.CryptsyUtils;

/**
 * @author ObsessiveOrange
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class CryptsyOrder {

  private final int tradeId;
  private final CryptsyOrderType type;
  private final Date time;
  private final BigDecimal price;
  private final BigDecimal quantity;
  private final BigDecimal total;

  /**
   * Constructor
   * 
   * @throws ParseException
   */
  @JsonCreator
  public CryptsyOrder(@JsonProperty("id") Integer tradeId, @JsonProperty("tradeid") Integer tradeId2, @JsonProperty("initiate_ordertype") CryptsyOrderType type, @JsonProperty("datetime") String time,
      @JsonProperty("tradeprice") BigDecimal price, @JsonProperty("quantity") BigDecimal quantity, @JsonProperty("total") BigDecimal total) throws ParseException {

    this.tradeId = tradeId == null ? tradeId2 : tradeId;
    this.type = type;
    this.time = time == null ? null : CryptsyUtils.convertDateTime(time);
    this.price = price;
    this.quantity = quantity;
    this.total = total;
  }

  public int getTradeId() {

    return tradeId;
  }

  public CryptsyOrderType getType() {

    return type;
  }

  public Date getTime() {

    return time;
  }

  public BigDecimal getPrice() {

    return price;
  }

  public BigDecimal getQuantity() {

    return quantity;
  }

  public BigDecimal getTotal() {

    return total;
  }

  @Override
  public String toString() {

    return "CryptsyOrder [Trade ID='" + tradeId + "',Time='" + time + "',Price='" + price + "',Quantity='" + quantity + "',Total='" + total + "]";
  }

  public static enum CryptsyOrderType {
    Buy, Sell
  }
}

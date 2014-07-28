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
package com.xeiam.xchange.cryptsy.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author ObsessiveOrange
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class CryptsyBuyOrder {

  private final BigDecimal buyPrice;
  private final BigDecimal quantity;
  private final BigDecimal total;

  @JsonCreator
  public CryptsyBuyOrder(@JsonProperty("buyprice") BigDecimal buyPrice, @JsonProperty("quantity") BigDecimal quantity, @JsonProperty("total") BigDecimal total) {

    this.buyPrice = buyPrice;
    this.quantity = quantity;
    this.total = total;
  }

  /**
   * @return the buyPrice
   */
  public BigDecimal getBuyPrice() {

    return buyPrice;
  }

  /**
   * @return the quantity
   */
  public BigDecimal getQuantity() {

    return quantity;
  }

  /**
   * @return the total
   */
  public BigDecimal getTotal() {

    return total;
  }

  /**
   * @return a string representation of this object
   */
  @Override
  public String toString() {

    return "CryptsyBuyOrder [" + "'Buy Price=" + buyPrice + "'Quantity=" + quantity + "'Total=" + total + "']";
  }
}

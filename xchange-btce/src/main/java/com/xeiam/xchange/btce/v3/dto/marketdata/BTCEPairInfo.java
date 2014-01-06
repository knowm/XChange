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
package com.xeiam.xchange.btce.v3.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Author: brox
 */

public class BTCEPairInfo {

  private final int decimals;
  private final BigDecimal minPrice;
  private final BigDecimal maxPrice;
  private final BigDecimal minAmount;
  private final int hidden;
  private final BigDecimal fee;

  public BTCEPairInfo(@JsonProperty("decimal_places") int decimals, @JsonProperty("min_price") BigDecimal minPrice, @JsonProperty("max_price") BigDecimal maxPrice,
      @JsonProperty("min_amount") BigDecimal minAmount, @JsonProperty("hidden") int hidden, @JsonProperty("fee") BigDecimal fee) {

    this.decimals = decimals;
    this.minPrice = minPrice;
    this.maxPrice = maxPrice;
    this.minAmount = minAmount;
    this.hidden = hidden;
    this.fee = fee;
  }

  public int getDecimals() {

    return decimals;
  }

  public BigDecimal getMinPrice() {

    return minPrice;
  }

  public BigDecimal getMaxPrice() {

    return maxPrice;
  }

  public BigDecimal getMinAmount() {

    return minAmount;
  }

  public int getHidden() {

    return hidden;
  }

  public BigDecimal getFee() {

    return fee;
  }

  @Override
  public String toString() {

    return "BTCEPairInfo [decimals=" + decimals + ", minPrice=" + minPrice + ", maxPrice=" + maxPrice + ", minAmount=" + minAmount + ", hidden=" + hidden + ", fee=" + fee + "]";
  }

}

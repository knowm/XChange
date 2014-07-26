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
package com.xeiam.xchange.mintpal.dto.marketdata;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.utils.DateUtils;

/**
 * @author jamespedwards42
 */
public class MintPalPublicTrade {

  private final String type;
  private final BigDecimal price;
  private final BigDecimal amount;
  private final BigDecimal total;
  private final Date time;

  public MintPalPublicTrade(@JsonProperty("type") String type, @JsonProperty("price") BigDecimal price, @JsonProperty("amount") BigDecimal amount, @JsonProperty("total") BigDecimal total,
      @JsonProperty("time") double time) {

    this.type = type;
    this.price = price;
    this.amount = amount;
    this.total = total;
    this.time = DateUtils.fromMillisUtc((long)time*1000);
    
  }

  public String getType() {
  
    return type;
  }

  
  public BigDecimal getPrice() {
  
    return price;
  }

  
  public BigDecimal getAmount() {
  
    return amount;
  }

  
  public BigDecimal getTotal() {
  
    return total;
  }

  
  public Date getTime() {
  
    return time;
  }

  @Override
  public String toString() {

    return "MintPalPublicTrade [type=" + type + ", price=" + price + ", amount=" + amount + ", total=" + total + ", time=" + time + "]";
  }
}

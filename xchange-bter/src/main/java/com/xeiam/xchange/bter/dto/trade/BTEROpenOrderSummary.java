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
package com.xeiam.xchange.bter.dto.trade;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by David Henry on 2/19/14.
 */
public class BTEROpenOrderSummary {
  private final String id;
  private final String sellCode;
  private final String buyCode;
  private final BigDecimal sellAmount;
  private final BigDecimal buyAmount;

  /**
   * Constructor
   *
   * @param id orderId
   * @param sellCode sell currency code String representation
   * @param buyCode buy currency code String representation
   * @param sellAmount amount to sell
   * @param buyAmount amount to buy
   */
  public BTEROpenOrderSummary(@JsonProperty("id") String id, @JsonProperty("sell_type") String sellCode, @JsonProperty("buy_type") String buyCode,
                              @JsonProperty("sell_amount") BigDecimal sellAmount, @JsonProperty("buy_amount") BigDecimal buyAmount) {
    this.id = id;
    this.sellCode = sellCode;
    this.buyCode = buyCode;
    this.sellAmount = sellAmount;
    this.buyAmount = buyAmount;
  }

  public String getId() {
    return id;
  }

  public String getSellCode() {
    return sellCode;
  }

  public String getBuyCode() {
    return buyCode;
  }

  public BigDecimal getSellAmount() {
    return sellAmount;
  }

  public BigDecimal getBuyAmount() {
    return buyAmount;
  }

  @Override
  public String toString() {
    return "BTEROpenOrderSummary{" +
            "id='" + id + '\'' +
            ", sellCode='" + sellCode + '\'' +
            ", buyCode='" + buyCode + '\'' +
            ", sellAmount=" + sellAmount +
            ", buyAmount=" + buyAmount +
            '}';
  }
}

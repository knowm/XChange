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
package com.xeiam.xchange.hitbtc.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

/**
 * @author kpysniak
 */
public class HitbtcSymbol {

  private final String symbol;
  private final BigDecimal step;
  private final BigDecimal lot;

  /**
   * Constructor
   *
   * @param symbol
   * @param step
   * @param lot
   */
  public HitbtcSymbol(@JsonProperty("symbol") String symbol, @JsonProperty("step") BigDecimal step, @JsonProperty("lot") BigDecimal lot) {

    this.symbol = symbol;
    this.step = step;
    this.lot = lot;
  }

  public String getSymbol() {

    return symbol;
  }

  public BigDecimal getStep() {

    return step;
  }

  public BigDecimal getLot() {

    return lot;
  }

  @Override
  public String toString() {

    return "HitbtcSymbol{" + "symbol='" + symbol + '\'' + ", step=" + step + ", lot=" + lot + '}';
  }
}

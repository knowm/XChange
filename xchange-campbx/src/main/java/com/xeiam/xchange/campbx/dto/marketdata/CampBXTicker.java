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
package com.xeiam.xchange.campbx.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

/**
 * @author Matija Mazi
 */
public final class CampBXTicker {

  private final BigDecimal last;
  private final BigDecimal bid;
  private final BigDecimal ask;
  private final String error;

  /**
   * Constructor
   * 
   * @param last
   * @param bid
   * @param ask
   * @param error
   */
  public CampBXTicker(@JsonProperty("Last Trade") BigDecimal last, @JsonProperty("Best Bid") BigDecimal bid, @JsonProperty("Best Ask") BigDecimal ask, @JsonProperty("Error") String error) {

    this.last = last;
    this.bid = bid;
    this.ask = ask;
    this.error = error;
  }

  public BigDecimal getLast() {

    return last;
  }

  public BigDecimal getBid() {

    return bid;
  }

  public BigDecimal getAsk() {

    return ask;
  }

  public String getError() {

    return error;
  }

  @Override
  public String toString() {

    return "CampBXTicker [last=" + last + ", bid=" + bid + ", ask=" + ask + ", error=" + error + "]";
  }

}

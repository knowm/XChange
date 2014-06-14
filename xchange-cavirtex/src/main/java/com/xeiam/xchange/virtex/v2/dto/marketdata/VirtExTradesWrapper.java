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
package com.xeiam.xchange.virtex.v2.dto.marketdata;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class VirtExTradesWrapper {

  private final ArrayList<VirtExTrade> trades;
  private final String status;
  private final String message;
  private final String apirate;

  /**
   * Constructor
   * 
   * @param trades
   * @param status
   * @param message
   * @param apirate
   */
  public VirtExTradesWrapper(@JsonProperty("orders") ArrayList<VirtExTrade> trades, @JsonProperty("message") String message, @JsonProperty("status") String status,
      @JsonProperty("apirate") String apirate) {

    this.trades = trades;
    this.status = status;
    this.message = message;
    this.apirate = apirate;
  }

  public ArrayList<VirtExTrade> getTrades() {

    return trades;
  }

  public String getStatus() {

    return status;
  }

  public String getMessage() {

    return message;
  }

  public String getApiRate() {

    return apirate;
  }

  @Override
  public String toString() {

    return "VirtExTrades [trades" + trades + ", status=" + status + ", message=" + message + ", apirate=" + apirate + "]";

  }

}

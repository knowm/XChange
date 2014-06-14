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
package com.xeiam.xchange.anx.v2.dto.marketdata;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ANXTickersWrapper {

  private final String result;
  private final Map<String, ANXTicker> anxTickers;
  private final String error;

  public ANXTickersWrapper(@JsonProperty("result") String result, @JsonProperty("data") Map<String, ANXTicker> anxTickers, @JsonProperty("error") String error) {

    this.result = result;
    this.anxTickers = anxTickers;
    this.error = error;
  }

  public String getResult() {

    return result;
  }

  public Map<String, ANXTicker> getAnxTickers() {

    return anxTickers;
  }

  public String getError() {

    return error;
  }
}

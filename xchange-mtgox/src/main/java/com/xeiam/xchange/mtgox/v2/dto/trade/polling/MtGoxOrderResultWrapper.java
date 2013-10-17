/**
 * Copyright (C) 2012 - 2013 Xeiam LLC http://xeiam.com
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
package com.xeiam.xchange.mtgox.v2.dto.trade.polling;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author timmolter
 */
public class MtGoxOrderResultWrapper {

  private final String result;
  private final MtGoxOrderResult mtGoxOrderResult;
  private final String error;

  /**
   * Constructor
   * 
   * @param result
   * @param mtGoxOrderResult
   * @param error
   */
  public MtGoxOrderResultWrapper(@JsonProperty("result") String result, @JsonProperty("data") MtGoxOrderResult mtGoxOrderResult, @JsonProperty("error") String error) {

    this.result = result;
    this.mtGoxOrderResult = mtGoxOrderResult;
    this.error = error;
  }

  public String getResult() {

    return result;
  }

  public MtGoxOrderResult getMtGoxOrderResult() {

    return mtGoxOrderResult;
  }

  public String getError() {

    return error;
  }

}

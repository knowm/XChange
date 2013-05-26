/**
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
package com.xeiam.xchange.mtgox.v2.dto.trade.polling;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author timmolter
 */
public class MtGoxLag {

  private final long lag;
  private final double lagDecimal;
  private final String lagText;
  private final int length;

  /**
   * Constructor
   * 
   * @param lag
   * @param lagDecimal
   * @param lagText
   * @param length
   */
  public MtGoxLag(@JsonProperty("lag") long lag, @JsonProperty("lag_secs") double lagDecimal, @JsonProperty("lag_text") String lagText, @JsonProperty("length") int length) {

    this.lag = lag;
    this.lagDecimal = lagDecimal;
    this.lagText = lagText;
    this.length = length;
  }

  public long getLag() {

    return lag;
  }

  public double getLagDecimal() {

    return lagDecimal;
  }

  public String getLagText() {

    return lagText;
  }

  public int getLength() {

    return length;
  }

  @Override
  public String toString() {

    return "MtGoxLag [lagText=" + lagText + "]";
  }

}

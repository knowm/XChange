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
package com.xeiam.xchange.cexio.dto.account;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Author: veken0m
 */
public class GHashIORejected {

  private final BigDecimal stale;
  private final BigDecimal duplicate;
  private final BigDecimal lowdiff;

  /**
   * @param stale
   * @param duplicate
   * @param lowdiff
   */
  public GHashIORejected(@JsonProperty("stale") BigDecimal stale, @JsonProperty("duplicate") BigDecimal duplicate, @JsonProperty("lowdiff") BigDecimal lowdiff) {

    this.stale = stale;
    this.duplicate = duplicate;
    this.lowdiff = lowdiff;
  }

  public BigDecimal getStale() {

    return stale;
  }

  public BigDecimal getDuplicate() {

    return duplicate;
  }

  public BigDecimal getLowdiff() {

    return lowdiff;
  }

}

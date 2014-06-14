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

public class GHashIOHashrate {

  private final BigDecimal last5m;
  private final BigDecimal last15m;
  private final BigDecimal last1h;
  private final BigDecimal last1d;
  private final BigDecimal prev5m;
  private final BigDecimal prev15m;
  private final BigDecimal prev1h;
  private final BigDecimal prev1d;

  /**
   * @param last5m
   * @param last15m
   * @param last1h
   * @param last1d
   * @param prev5m
   * @param prev15m
   * @param prev1h
   * @param prev1d
   */
  public GHashIOHashrate(@JsonProperty("last5m") BigDecimal last5m, @JsonProperty("last15m") BigDecimal last15m, @JsonProperty("last1h") BigDecimal last1h, @JsonProperty("last1d") BigDecimal last1d,
      @JsonProperty("prev5m") BigDecimal prev5m, @JsonProperty("prev15m") BigDecimal prev15m, @JsonProperty("prev1h") BigDecimal prev1h, @JsonProperty("prev1d") BigDecimal prev1d) {

    this.last5m = last5m;
    this.last15m = last15m;
    this.last1h = last1h;
    this.last1d = last1d;
    this.prev5m = prev5m;
    this.prev15m = prev15m;
    this.prev1h = prev1h;
    this.prev1d = prev1d;
  }

  public BigDecimal getLast5m() {

    return last5m;
  }

  public BigDecimal getLast15m() {

    return last15m;
  }

  public BigDecimal getLast1h() {

    return last1h;
  }

  public BigDecimal getLast1d() {

    return last1d;
  }

  public BigDecimal getPrev5m() {

    return prev5m;
  }

  public BigDecimal getPrev15m() {

    return prev15m;
  }

  public BigDecimal getPrev1h() {

    return prev1h;
  }

  public BigDecimal getPrev1d() {

    return prev1d;
  }

}

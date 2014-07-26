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
package com.xeiam.xchange.bitcurex.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BitcurexFunds {

  private final BigDecimal eurs;
  private final BigDecimal plns;
  private final BigDecimal btcs;
  private final String address;
  private final String error;

  public BitcurexFunds(@JsonProperty("eurs") BigDecimal eurs, @JsonProperty("plns") BigDecimal plns, @JsonProperty("btcs") BigDecimal btcs, @JsonProperty("address") String address,
      @JsonProperty("error") String error) {

    this.eurs = eurs;
    this.plns = plns;
    this.btcs = btcs;
    this.address = address;
    this.error = error;
  }

  public BigDecimal getEurs() {

    return eurs;
  }

  public BigDecimal getPlns() {

    return plns;
  }

  public BigDecimal getBtcs() {

    return btcs;
  }

  public String getAddress() {

    return address;
  }

  public String getError() {

    return error;
  }
}

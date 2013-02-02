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
package com.xeiam.xchange.bitcoincentral.dto.account;

import java.math.BigDecimal;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author Matija Mazi
 */
public final class BitcoinCentralAccountInfo {

  private final BigDecimal eur;
  private final BigDecimal btc;
  private final BigDecimal gbp;
  private final BigDecimal usd;
  private final String address;
  private final BigDecimal unconfirmedBtc;

  public BitcoinCentralAccountInfo(@JsonProperty("EUR") BigDecimal eur, @JsonProperty("BTC") BigDecimal btc, @JsonProperty("GBP") BigDecimal gbp, @JsonProperty("USD") BigDecimal usd,
      @JsonProperty("address") String address, @JsonProperty("UNCONFIRMED_BTC") BigDecimal unconfirmedBtc) {

    this.eur = eur;
    this.btc = btc;
    this.gbp = gbp;
    this.usd = usd;
    this.address = address;
    this.unconfirmedBtc = unconfirmedBtc;
  }

  public BigDecimal getEur() {

    return eur;
  }

  public BigDecimal getBtc() {

    return btc;
  }

  public BigDecimal getGbp() {

    return gbp;
  }

  public BigDecimal getUsd() {

    return usd;
  }

  public String getAddress() {

    return address;
  }

  public BigDecimal getUnconfirmedBtc() {

    return unconfirmedBtc;
  }

}

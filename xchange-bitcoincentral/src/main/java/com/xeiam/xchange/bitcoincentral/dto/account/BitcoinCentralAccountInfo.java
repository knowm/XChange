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

  private final @JsonProperty("address")
  String address;
  private final @JsonProperty("BTC")
  BigDecimal btc;
  private final @JsonProperty("CAD")
  BigDecimal cad;
  private final @JsonProperty("EUR")
  BigDecimal eur;
  private final @JsonProperty("INR")
  BigDecimal inr;
  private final @JsonProperty("LREUR")
  BigDecimal lreur;
  private final @JsonProperty("LRUSD")
  BigDecimal lrusd;
  private final @JsonProperty("PGAU")
  BigDecimal pgau;
  private final @JsonProperty("UNCONFIRMED_BTC")
  BigDecimal unconfirmedBtc;

  public BitcoinCentralAccountInfo(@JsonProperty("address") String address, @JsonProperty("BTC") BigDecimal btc, @JsonProperty("CAD") BigDecimal cad, @JsonProperty("EUR") BigDecimal eur,
      @JsonProperty("INR") BigDecimal inr, @JsonProperty("LREUR") BigDecimal lreur, @JsonProperty("LRUSD") BigDecimal lrusd, @JsonProperty("PGAU") BigDecimal pgau,
      @JsonProperty("UNCONFIRMED_BTC") BigDecimal unconfirmedBtc) {

    this.address = address;
    this.btc = btc;
    this.cad = cad;
    this.eur = eur;
    this.inr = inr;
    this.lreur = lreur;
    this.lrusd = lrusd;
    this.pgau = pgau;
    this.unconfirmedBtc = unconfirmedBtc;
  }

  public String getAddress() {

    return address;
  }

  public BigDecimal getBtc() {

    return btc;
  }

  public BigDecimal getCad() {

    return cad;
  }

  public BigDecimal getEur() {

    return eur;
  }

  public BigDecimal getInr() {

    return inr;
  }

  public BigDecimal getLreur() {

    return lreur;
  }

  public BigDecimal getLrusd() {

    return lrusd;
  }

  public BigDecimal getPgau() {

    return pgau;
  }

  public BigDecimal getUnconfirmedBtc() {

    return unconfirmedBtc;
  }

  @Override
  public String toString() {

    return "BitcoinCentralAccountInfo [address=" + address + ", btc=" + btc + ", cad=" + cad + ", eur=" + eur + ", inr=" + inr + ", lreur=" + lreur + ", lrusd=" + lrusd + ", pgau=" + pgau
        + ", unconfirmedBtc=" + unconfirmedBtc + "]";
  }

}

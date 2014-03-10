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
package com.xeiam.xchange.anx.v2.dto.account.polling;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data object representing Wallets from Mt Gox
 */
public final class Wallets {

  private final ANXWallet bTC;
  private final ANXWallet uSD;
  private final ANXWallet eUR;
  private final ANXWallet gBP;
  private final ANXWallet aUD;
  private final ANXWallet cAD;
  private final ANXWallet cHF;
  private final ANXWallet jPY;
  private final ANXWallet cNY;
  private final ANXWallet dKK;
  private final ANXWallet hKD;
  private final ANXWallet nZD;
  private final ANXWallet pLN;
  private final ANXWallet rUB;
  private final ANXWallet sEK;
  private final ANXWallet tHB;
  private final ANXWallet nOK;
  private final ANXWallet sGD;

  /**
   * Constructor
   * 
   * @param bTC
   * @param uSD
   * @param eUR
   * @param gBP
   * @param aUD
   * @param cAD
   * @param cHF
   * @param jPY
   * @param cNY
   * @param dKK
   * @param hKD
   * @param nZD
   * @param pLN
   * @param rUB
   * @param sEK
   * @param tHB
   * @param nOK
   * @param sGD
   */
  public Wallets(@JsonProperty("BTC") ANXWallet bTC, @JsonProperty("USD") ANXWallet uSD, @JsonProperty("EUR") ANXWallet eUR, @JsonProperty("GBP") ANXWallet gBP,
      @JsonProperty("AUD") ANXWallet aUD, @JsonProperty("CAD") ANXWallet cAD, @JsonProperty("CHF") ANXWallet cHF, @JsonProperty("JPY") ANXWallet jPY, @JsonProperty("CNY") ANXWallet cNY,
      @JsonProperty("DKK") ANXWallet dKK, @JsonProperty("HKD") ANXWallet hKD, @JsonProperty("NZD") ANXWallet nZD, @JsonProperty("PLN") ANXWallet pLN, @JsonProperty("RUB") ANXWallet rUB,
      @JsonProperty("SEK") ANXWallet sEK, @JsonProperty("THB") ANXWallet tHB, @JsonProperty("NOK") ANXWallet nOK, @JsonProperty("SGD") ANXWallet sGD) {

    this.bTC = bTC;
    this.uSD = uSD;
    this.eUR = eUR;
    this.gBP = gBP;
    this.aUD = aUD;
    this.cAD = cAD;
    this.cHF = cHF;
    this.jPY = jPY;
    this.cNY = cNY;
    this.dKK = dKK;
    this.hKD = hKD;
    this.nZD = nZD;
    this.pLN = pLN;
    this.rUB = rUB;
    this.sEK = sEK;
    this.tHB = tHB;
    this.nOK = nOK;
    this.sGD = sGD;
  }

  public ANXWallet getBTC() {

    return this.bTC;
  }

  public ANXWallet getUSD() {

    return this.uSD;
  }

  public ANXWallet getEUR() {

    return eUR;
  }

  public ANXWallet getGBP() {

    return gBP;
  }

  public ANXWallet getAUD() {

    return aUD;
  }

  public ANXWallet getCAD() {

    return cAD;
  }

  public ANXWallet getCHF() {

    return cHF;
  }

  public ANXWallet getJPY() {

    return jPY;
  }

  public ANXWallet getCNY() {

    return cNY;
  }

  public ANXWallet getDKK() {

    return dKK;
  }

  public ANXWallet getHKD() {

    return hKD;
  }

  public ANXWallet getNZD() {

    return nZD;
  }

  public ANXWallet getPLN() {

    return pLN;
  }

  public ANXWallet getRUB() {

    return rUB;
  }

  public ANXWallet getSEK() {

    return sEK;
  }

  public ANXWallet getTHB() {

    return tHB;
  }

  public ANXWallet getNOK() {

    return nOK;
  }

  public ANXWallet getSGD() {

    return sGD;
  }

  public List<ANXWallet> getANXWallets() {

    return Arrays.asList(bTC, uSD, eUR, gBP, aUD, cAD, cHF, jPY, cNY, dKK, hKD, nZD, pLN, rUB, sEK, tHB, nOK, sGD);
  }

  @Override
  public String toString() {

    return "Wallets [BTC=" + bTC + ", USD=" + uSD + ", EUR=" + eUR + ", GBP=" + gBP + ", AUD=" + aUD + ", CAD=" + cAD + ", CHF=" + cHF + ", JPY=" + jPY + ", CNY=" + cNY + ", DKK=" + dKK + ", HKD="
        + hKD + ", NZD=" + nZD + ", PLN=" + pLN + ", RUB=" + rUB + ", SEK=" + sEK + ", THB=" + tHB + ", NOK=" + nOK + ", SGD=" + sGD + "]";
  }

}

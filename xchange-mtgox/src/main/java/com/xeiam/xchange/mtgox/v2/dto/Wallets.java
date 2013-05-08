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
package com.xeiam.xchange.mtgox.v2.dto;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data object representing Wallets from Mt Gox
 */
public final class Wallets {

  private final MtGoxWallet bTC;
  private final MtGoxWallet uSD;
  private final MtGoxWallet eUR;
  private final MtGoxWallet gBP;
  private final MtGoxWallet aUD;
  private final MtGoxWallet cAD;
  private final MtGoxWallet cHF;
  private final MtGoxWallet jPY;
  private final MtGoxWallet cNY;
  private final MtGoxWallet dKK;
  private final MtGoxWallet hKD;
  private final MtGoxWallet nZD;
  private final MtGoxWallet pLN;
  private final MtGoxWallet rUB;
  private final MtGoxWallet sEK;
  private final MtGoxWallet tHB;

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
   */
  public Wallets(@JsonProperty("BTC") MtGoxWallet bTC, @JsonProperty("USD") MtGoxWallet uSD, @JsonProperty("EUR") MtGoxWallet eUR, @JsonProperty("GBP") MtGoxWallet gBP,
      @JsonProperty("AUD") MtGoxWallet aUD, @JsonProperty("CAD") MtGoxWallet cAD, @JsonProperty("CHF") MtGoxWallet cHF, @JsonProperty("JPY") MtGoxWallet jPY, @JsonProperty("CNY") MtGoxWallet cNY,
      @JsonProperty("DKK") MtGoxWallet dKK, @JsonProperty("HKD") MtGoxWallet hKD, @JsonProperty("NZD") MtGoxWallet nZD, @JsonProperty("PLN") MtGoxWallet pLN, @JsonProperty("RUB") MtGoxWallet rUB,
      @JsonProperty("SEK") MtGoxWallet sEK, @JsonProperty("THB") MtGoxWallet tHB) {

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
  }

  public MtGoxWallet getBTC() {

    return this.bTC;
  }

  public MtGoxWallet getUSD() {

    return this.uSD;
  }

  public MtGoxWallet getEUR() {

    return eUR;
  }

  public MtGoxWallet getGBP() {

    return gBP;
  }

  public MtGoxWallet getAUD() {

    return aUD;
  }

  public MtGoxWallet getCAD() {

    return cAD;
  }

  public MtGoxWallet getCHF() {

    return cHF;
  }

  public MtGoxWallet getJPY() {

    return jPY;
  }

  public MtGoxWallet getCNY() {

    return cNY;
  }

  public MtGoxWallet getDKK() {

    return dKK;
  }

  public MtGoxWallet getHKD() {

    return hKD;
  }

  public MtGoxWallet getNZD() {

    return nZD;
  }

  public MtGoxWallet getPLN() {

    return pLN;
  }

  public MtGoxWallet getRUB() {

    return rUB;
  }

  public MtGoxWallet getSEK() {

    return sEK;
  }

  public MtGoxWallet getTHB() {

    return tHB;
  }

  public List<MtGoxWallet> getMtGoxWallets() {

    return Arrays.asList(bTC, uSD, eUR, gBP, aUD, cAD, cHF, jPY, cNY, dKK, hKD, nZD, pLN, rUB, sEK, tHB);
  }

  @Override
  public String toString() {

    return "Wallets [BTC=" + bTC + ", USD=" + uSD + ", EUR=" + eUR + ", GBP=" + gBP + ", AUD=" + aUD + ", CAD=" + cAD + ", CHF=" + cHF + ", JPY=" + jPY + ", CNY=" + cNY + ", DKK=" + dKK + ", HKD="
        + hKD + ", NZD=" + nZD + ", PLN=" + pLN + ", RUB=" + rUB + ", SEK=" + sEK + ", THB=" + tHB + "]";
  }

}

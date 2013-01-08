/**
 * Copyright (C) 2012 Xeiam LLC http://xeiam.com
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
package com.xeiam.xchange.mtgox.v1.dto.trade;

import java.util.Arrays;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Data object representing Wallets from Mt Gox
 * 
 * @immutable
 */
public final class Wallets {

  private final MtGoxWallet BTC;
  private final MtGoxWallet USD;
  private final MtGoxWallet EUR;
  private final MtGoxWallet GBP;
  private final MtGoxWallet AUD;
  private final MtGoxWallet CAD;
  private final MtGoxWallet CHF;
  private final MtGoxWallet JPY;
  private final MtGoxWallet CNY;
  private final MtGoxWallet DKK;
  private final MtGoxWallet HKD;
  private final MtGoxWallet NZD;
  private final MtGoxWallet PLN;
  private final MtGoxWallet RUB;
  private final MtGoxWallet SEK;
  private final MtGoxWallet THB;

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

    BTC = bTC;
    USD = uSD;
    EUR = eUR;
    GBP = gBP;
    AUD = aUD;
    CAD = cAD;
    CHF = cHF;
    JPY = jPY;
    CNY = cNY;
    DKK = dKK;
    HKD = hKD;
    NZD = nZD;
    PLN = pLN;
    RUB = rUB;
    SEK = sEK;
    THB = tHB;
  }

  public MtGoxWallet getBTC() {

    return this.BTC;
  }

  public MtGoxWallet getUSD() {

    return this.USD;
  }

  public MtGoxWallet getEUR() {

    return EUR;
  }

  public MtGoxWallet getGBP() {

    return GBP;
  }

  public MtGoxWallet getAUD() {

    return AUD;
  }

  public MtGoxWallet getCAD() {

    return CAD;
  }

  public MtGoxWallet getCHF() {

    return CHF;
  }

  public MtGoxWallet getJPY() {

    return JPY;
  }

  public MtGoxWallet getCNY() {

    return CNY;
  }

  public MtGoxWallet getDKK() {

    return DKK;
  }

  public MtGoxWallet getHKD() {

    return HKD;
  }

  public MtGoxWallet getNZD() {

    return NZD;
  }

  public MtGoxWallet getPLN() {

    return PLN;
  }

  public MtGoxWallet getRUB() {

    return RUB;
  }

  public MtGoxWallet getSEK() {

    return SEK;
  }

  public MtGoxWallet getTHB() {

    return THB;
  }

  public List<MtGoxWallet> getMtGoxWallets() {

    return Arrays.asList(BTC, USD, EUR, GBP, AUD, CAD, CHF, JPY, CNY, DKK, HKD, NZD, PLN, RUB, SEK, THB);
  }

  @Override
  public String toString() {

    return "Wallets [BTC=" + BTC + ", USD=" + USD + ", EUR=" + EUR + ", GBP=" + GBP + ", AUD=" + AUD + ", CAD=" + CAD + ", CHF=" + CHF + ", JPY=" + JPY + ", CNY=" + CNY + ", DKK=" + DKK + ", HKD="
        + HKD + ", NZD=" + NZD + ", PLN=" + PLN + ", RUB=" + RUB + ", SEK=" + SEK + ", THB=" + THB + "]";
  }

}

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
 */
public class Wallets {

  private MtGoxWallet BTC = new MtGoxWallet();
  private MtGoxWallet USD = new MtGoxWallet();
  private MtGoxWallet EUR = new MtGoxWallet();
  private MtGoxWallet GBP = new MtGoxWallet();
  private MtGoxWallet AUD = new MtGoxWallet();
  private MtGoxWallet CAD = new MtGoxWallet();
  private MtGoxWallet CHF = new MtGoxWallet();
  private MtGoxWallet JPY = new MtGoxWallet();
  private MtGoxWallet CNY = new MtGoxWallet();
  private MtGoxWallet DKK = new MtGoxWallet();
  private MtGoxWallet HKD = new MtGoxWallet();
  private MtGoxWallet NZD = new MtGoxWallet();
  private MtGoxWallet PLN = new MtGoxWallet();
  private MtGoxWallet RUB = new MtGoxWallet();
  private MtGoxWallet SEK = new MtGoxWallet();
  private MtGoxWallet THB = new MtGoxWallet();

  @JsonProperty("BTC")
  public MtGoxWallet getBTC() {
    return this.BTC;
  }

  public void setBTC(MtGoxWallet bTC) {
    this.BTC = bTC;
  }

  @JsonProperty("USD")
  public MtGoxWallet getUSD() {
    return this.USD;
  }

  public void setUSD(MtGoxWallet uSD) {
    this.USD = uSD;
  }

  @JsonProperty("EUR")
  public MtGoxWallet getEUR() {
    return EUR;
  }

  public void setEUR(MtGoxWallet eUR) {
    EUR = eUR;
  }

  @JsonProperty("GBP")
  public MtGoxWallet getGBP() {
    return GBP;
  }

  public void setGBP(MtGoxWallet gBP) {
    GBP = gBP;
  }

  @JsonProperty("AUD")
  public MtGoxWallet getAUD() {
    return AUD;
  }

  public void setAUD(MtGoxWallet aUD) {
    AUD = aUD;
  }

  @JsonProperty("CAD")
  public MtGoxWallet getCAD() {
    return CAD;
  }

  public void setCAD(MtGoxWallet cAD) {
    CAD = cAD;
  }

  @JsonProperty("CHF")
  public MtGoxWallet getCHF() {
    return CHF;
  }

  public void setCHF(MtGoxWallet cHF) {
    CHF = cHF;
  }

  @JsonProperty("JPY")
  public MtGoxWallet getJPY() {
    return JPY;
  }

  public void setJPY(MtGoxWallet jPY) {
    JPY = jPY;
  }

  @JsonProperty("CNY")
  public MtGoxWallet getCNY() {
    return CNY;
  }

  public void setCNY(MtGoxWallet cNY) {
    CNY = cNY;
  }

  @JsonProperty("DKK")
  public MtGoxWallet getDKK() {
    return DKK;
  }

  public void setDKK(MtGoxWallet dKK) {
    DKK = dKK;
  }

  @JsonProperty("HKD")
  public MtGoxWallet getHKD() {
    return HKD;
  }

  public void setHKD(MtGoxWallet hKD) {
    HKD = hKD;
  }

  @JsonProperty("NZD")
  public MtGoxWallet getNZD() {
    return NZD;
  }

  public void setNZD(MtGoxWallet nZD) {
    NZD = nZD;
  }

  @JsonProperty("PLN")
  public MtGoxWallet getPLN() {
    return PLN;
  }

  public void setPLN(MtGoxWallet pLN) {
    PLN = pLN;
  }

  @JsonProperty("RUB")
  public MtGoxWallet getRUB() {
    return RUB;
  }

  public void setRUB(MtGoxWallet rUB) {
    RUB = rUB;
  }

  @JsonProperty("SEK")
  public MtGoxWallet getSEK() {
    return SEK;
  }

  public void setSEK(MtGoxWallet sEK) {
    SEK = sEK;
  }

  @JsonProperty("THB")
  public MtGoxWallet getTHB() {
    return THB;
  }

  public void setTHB(MtGoxWallet tHB) {
    THB = tHB;
  }

  public List<MtGoxWallet> getMtGoxWallets() {
    return Arrays.asList(BTC, USD, EUR, GBP, AUD, CAD, CHF, JPY, CNY, DKK, HKD, NZD, PLN, RUB, SEK, THB);
  }

  @Override
  public String toString() {
    return "Wallets [BTC=" + BTC + ", USD=" + USD + ", EUR=" + EUR + ", GBP=" + GBP + ", AUD=" + AUD + ", CAD=" + CAD + ", CHF=" + CHF + ", JPY=" + JPY + ", CNY=" + CNY + ", DKK=" + DKK + ", HKD=" + HKD + ", NZD=" + NZD
        + ", PLN=" + PLN + ", RUB=" + RUB + ", SEK=" + SEK + ", THB=" + THB + "]";
  }

}

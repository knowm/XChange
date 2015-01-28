package com.xeiam.xchange.anx.v2.dto.account.polling;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data object representing Wallets from ANX
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
  private final ANXWallet hKD;
  private final ANXWallet nZD;
  private final ANXWallet sGD;

  //
  private final ANXWallet lTC;
  private final ANXWallet nMC;
  private final ANXWallet mEC;
  private final ANXWallet sBC;
  private final ANXWallet wDC;
  private final ANXWallet qRK;
  private final ANXWallet pPC;
  private final ANXWallet dOGE;
  private final ANXWallet nXT;
  private final ANXWallet mSC;

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
   * @param hKD
   * @param nZD
   * @param sGD
   */
  public Wallets(@JsonProperty("BTC") ANXWallet bTC, @JsonProperty("USD") ANXWallet uSD, @JsonProperty("EUR") ANXWallet eUR,
      @JsonProperty("GBP") ANXWallet gBP, @JsonProperty("AUD") ANXWallet aUD, @JsonProperty("CAD") ANXWallet cAD, @JsonProperty("CHF") ANXWallet cHF,
      @JsonProperty("JPY") ANXWallet jPY, @JsonProperty("CNY") ANXWallet cNY, @JsonProperty("HKD") ANXWallet hKD, @JsonProperty("NZD") ANXWallet nZD,
      @JsonProperty("SGD") ANXWallet sGD, @JsonProperty("LTC") ANXWallet lTC, @JsonProperty("NMC") ANXWallet nMC, @JsonProperty("MEC") ANXWallet mEC,
      @JsonProperty("SBC") ANXWallet sBC, @JsonProperty("WDC") ANXWallet wDC, @JsonProperty("QRK") ANXWallet qRK, @JsonProperty("PPC") ANXWallet pPC,
      @JsonProperty("DOGE") ANXWallet dOGE, @JsonProperty("NXT") ANXWallet nXT, @JsonProperty("MSC") ANXWallet mSC) {

    this.bTC = bTC;
    this.uSD = uSD;
    this.eUR = eUR;
    this.gBP = gBP;
    this.aUD = aUD;
    this.cAD = cAD;
    this.cHF = cHF;
    this.jPY = jPY;
    this.cNY = cNY;
    this.hKD = hKD;
    this.nZD = nZD;
    this.sGD = sGD;

    this.lTC = lTC;
    this.nMC = nMC;
    this.mEC = mEC;
    this.sBC = sBC;
    this.wDC = wDC;
    this.qRK = qRK;
    this.pPC = pPC;
    this.dOGE = dOGE;
    this.nXT = nXT;
    this.mSC = mSC;
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

  public ANXWallet getHKD() {

    return hKD;
  }

  public ANXWallet getNZD() {

    return nZD;
  }

  public ANXWallet getSGD() {

    return sGD;
  }

  //

  public ANXWallet getLTC() {

    return lTC;
  }

  public ANXWallet getNMC() {

    return nMC;
  }

  public ANXWallet getMEC() {

    return mEC;
  }

  public ANXWallet getSBC() {

    return sBC;
  }

  public ANXWallet getWDC() {

    return wDC;
  }

  public ANXWallet getQRK() {

    return qRK;
  }

  public ANXWallet getPPC() {

    return pPC;
  }

  public ANXWallet getDOGE() {

    return dOGE;
  }

  public ANXWallet getNXT() {

    return nXT;
  }

  public ANXWallet getMSC() {

    return mSC;
  }

  public List<ANXWallet> getANXWallets() {

    return Arrays.asList(bTC, uSD, eUR, gBP, aUD, cAD, cHF, jPY, cNY, hKD, nZD, sGD, lTC, nMC, mEC, sBC, wDC, qRK, pPC, dOGE, nXT, mSC);
  }

  @Override
  public String toString() {

    return "Wallets [BTC=" + bTC + ", USD=" + uSD + ", EUR=" + eUR + ", GBP=" + gBP + ", AUD=" + aUD + ", CAD=" + cAD + ", CHF=" + cHF + ", JPY="
        + jPY + ", CNY=" + cNY + ", HKD=" + hKD + ", NZD=" + nZD + ", SGD=" + sGD + ", LTC=" + lTC + ", NMC=" + nMC + ", MEC=" + mEC + ", SBC=" + sBC
        + ", WDC=" + wDC + ", QRK=" + qRK + ", PPC=" + pPC + ", DOGE=" + dOGE + ", NXT=" + nXT + ", MSC=" + mSC + "]";
  }

}

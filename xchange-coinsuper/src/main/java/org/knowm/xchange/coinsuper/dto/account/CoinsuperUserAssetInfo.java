package org.knowm.xchange.coinsuper.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class CoinsuperUserAssetInfo {

  @JsonProperty("asset")
  private Asset asset;

  @JsonProperty("email")
  private String email;

  @JsonProperty("userNo")
  private long userNo;

  /** No args constructor for use in serialization */
  public CoinsuperUserAssetInfo() {}

  /**
   * @param asset
   * @param email
   * @param userNo
   */
  public CoinsuperUserAssetInfo(Asset asset, String email, long userNo) {
    super();
    this.asset = asset;
    this.email = email;
    this.userNo = userNo;
  }

  @JsonProperty("asset")
  public Asset getAsset() {
    return asset;
  }

  @JsonProperty("asset")
  public void setAsset(Asset asset) {
    this.asset = asset;
  }

  @JsonProperty("email")
  public String getEmail() {
    return email;
  }

  @JsonProperty("email")
  public void setEmail(String email) {
    this.email = email;
  }

  @JsonProperty("userNo")
  public long getUserNo() {
    return userNo;
  }

  @JsonProperty("userNo")
  public void setUserNo(long userNo) {
    this.userNo = userNo;
  }
  // ----------------------------------------------
  public static class Asset {
    @JsonProperty("AAB")
    private CoinsuperBalance aAB;

    @JsonProperty("AMR")
    private CoinsuperBalance aMR;

    @JsonProperty("BCH")
    private CoinsuperBalance bCH;

    @JsonProperty("BTC")
    private CoinsuperBalance bTC;

    @JsonProperty("BUC")
    private CoinsuperBalance bUC;

    @JsonProperty("CCCX")
    private CoinsuperBalance cCCX;

    @JsonProperty("CEEK")
    private CoinsuperBalance cEEK;

    @JsonProperty("CEN")
    private CoinsuperBalance cEN;

    @JsonProperty("DASH")
    private CoinsuperBalance dASH;

    @JsonProperty("DX")
    private CoinsuperBalance dX;

    @JsonProperty("EOS")
    private CoinsuperBalance eOS;

    @JsonProperty("ETC")
    private CoinsuperBalance eTC;

    @JsonProperty("ETH")
    private CoinsuperBalance eTH;

    @JsonProperty("ETK")
    private CoinsuperBalance eTK;

    @JsonProperty("HOLD")
    private CoinsuperBalance hOLD;

    @JsonProperty("IOVC")
    private CoinsuperBalance iOVC;

    @JsonProperty("LTC")
    private CoinsuperBalance lTC;

    @JsonProperty("MEDX")
    private CoinsuperBalance mEDX;

    @JsonProperty("NEO")
    private CoinsuperBalance nEO;

    @JsonProperty("NEWOS")
    private CoinsuperBalance nEWOS;

    @JsonProperty("OMG")
    private CoinsuperBalance oMG;

    @JsonProperty("PRL")
    private CoinsuperBalance pRL;

    @JsonProperty("QTUM")
    private CoinsuperBalance qTUM;

    @JsonProperty("RLM")
    private CoinsuperBalance rLM;

    @JsonProperty("TEST")
    private CoinsuperBalance tEST;

    @JsonProperty("THRT")
    private CoinsuperBalance tHRT;

    @JsonProperty("USD")
    private CoinsuperBalance uSD;

    @JsonProperty("UST")
    private CoinsuperBalance uST;

    @JsonProperty("XCCC")
    private CoinsuperBalance xCCC;

    @JsonProperty("XEM")
    private CoinsuperBalance xEM;

    @JsonProperty("XRP")
    private CoinsuperBalance xRP;

    public Asset() {}

    public Asset(
        CoinsuperBalance aAB,
        CoinsuperBalance aMR,
        CoinsuperBalance bCH,
        CoinsuperBalance bTC,
        CoinsuperBalance bUC,
        CoinsuperBalance cCCX,
        CoinsuperBalance cEEK,
        CoinsuperBalance cEN,
        CoinsuperBalance dASH,
        CoinsuperBalance dX,
        CoinsuperBalance eOS,
        CoinsuperBalance eTC,
        CoinsuperBalance eTH,
        CoinsuperBalance eTK,
        CoinsuperBalance hOLD,
        CoinsuperBalance iOVC,
        CoinsuperBalance lTC,
        CoinsuperBalance mEDX,
        CoinsuperBalance nEO,
        CoinsuperBalance nEWOS,
        CoinsuperBalance oMG,
        CoinsuperBalance pRL,
        CoinsuperBalance qTUM,
        CoinsuperBalance rLM,
        CoinsuperBalance tEST,
        CoinsuperBalance tHRT,
        CoinsuperBalance uSD,
        CoinsuperBalance uST,
        CoinsuperBalance xCCC,
        CoinsuperBalance xEM,
        CoinsuperBalance xRP) {
      super();
      this.aAB = aAB;
      this.aMR = aMR;
      this.bCH = bCH;
      this.bTC = bTC;
      this.bUC = bUC;
      this.cCCX = cCCX;
      this.cEEK = cEEK;
      this.cEN = cEN;
      this.dASH = dASH;
      this.dX = dX;
      this.eOS = eOS;
      this.eTC = eTC;
      this.eTH = eTH;
      this.eTK = eTK;
      this.hOLD = hOLD;
      this.iOVC = iOVC;
      this.lTC = lTC;
      this.mEDX = mEDX;
      this.nEO = nEO;
      this.nEWOS = nEWOS;
      this.oMG = oMG;
      this.pRL = pRL;
      this.qTUM = qTUM;
      this.rLM = rLM;
      this.tEST = tEST;
      this.tHRT = tHRT;
      this.uSD = uSD;
      this.uST = uST;
      this.xCCC = xCCC;
      this.xEM = xEM;
      this.xRP = xRP;
    }

    public CoinsuperBalance getAnyAssetBalance(String currency) {
      if (currency.equals("AAB")) return this.aAB;
      if (currency.equals("AMR")) return this.aMR;
      if (currency.equals("BCH")) return this.bCH;
      if (currency.equals("BTC")) return this.bTC;
      if (currency.equals("CCCX")) return this.cCCX;
      if (currency.equals("CEEK")) return this.cEEK;
      if (currency.equals("CEN")) return this.cEN;
      if (currency.equals("DASH")) return this.dASH;
      if (currency.equals("DX")) return this.dX;
      if (currency.equals("EOS")) return this.eOS;
      if (currency.equals("ETC")) return this.eTC;
      if (currency.equals("ETH")) return this.eTH;
      if (currency.equals("ETK")) return this.eTK;
      if (currency.equals("HOLD")) return this.hOLD;
      if (currency.equals("IOVC")) return this.iOVC;
      if (currency.equals("LTC")) return this.lTC;
      if (currency.equals("MEDX")) return this.mEDX;
      if (currency.equals("NEO")) return this.nEO;
      if (currency.equals("NEWOS")) return this.nEWOS;
      if (currency.equals("OMG")) return this.oMG;
      if (currency.equals("PRL")) return this.pRL;
      if (currency.equals("QTUM")) return this.qTUM;
      if (currency.equals("RLM")) return this.rLM;
      if (currency.equals("TEST")) return this.tEST;
      if (currency.equals("THRT")) return this.tHRT;
      if (currency.equals("USD")) return this.uSD;
      if (currency.equals("UST")) return this.uST;
      if (currency.equals("XCCC")) return this.xCCC;
      if (currency.equals("XEM")) return this.xEM;
      if (currency.equals("XRP")) return this.xRP;

      return this.bTC;
    }

    @JsonProperty("AAB")
    public CoinsuperBalance getAAB() {
      return aAB;
    }

    @JsonProperty("AAB")
    public void setAAB(CoinsuperBalance aAB) {
      this.aAB = aAB;
    }

    @JsonProperty("AMR")
    public CoinsuperBalance getAMR() {
      return aMR;
    }

    @JsonProperty("AMR")
    public void setAMR(CoinsuperBalance aMR) {
      this.aMR = aMR;
    }

    @JsonProperty("BCH")
    public CoinsuperBalance getBCH() {
      return bCH;
    }

    @JsonProperty("BCH")
    public void setBCH(CoinsuperBalance bCH) {
      this.bCH = bCH;
    }

    @JsonProperty("BTC")
    public CoinsuperBalance getBTC() {
      return bTC;
    }

    @JsonProperty("BTC")
    public void setBTC(CoinsuperBalance bTC) {
      this.bTC = bTC;
    }

    @JsonProperty("BUC")
    public CoinsuperBalance getBUC() {
      return bUC;
    }

    @JsonProperty("BUC")
    public void setBUC(CoinsuperBalance bUC) {
      this.bUC = bUC;
    }

    @JsonProperty("CCCX")
    public CoinsuperBalance getCCCX() {
      return cCCX;
    }

    @JsonProperty("CCCX")
    public void setCCCX(CoinsuperBalance cCCX) {
      this.cCCX = cCCX;
    }

    @JsonProperty("CEEK")
    public CoinsuperBalance getCEEK() {
      return cEEK;
    }

    @JsonProperty("CEEK")
    public void setCEEK(CoinsuperBalance cEEK) {
      this.cEEK = cEEK;
    }

    @JsonProperty("CEN")
    public CoinsuperBalance getCEN() {
      return cEN;
    }

    @JsonProperty("CEN")
    public void setCEN(CoinsuperBalance cEN) {
      this.cEN = cEN;
    }

    @JsonProperty("DASH")
    public CoinsuperBalance getDASH() {
      return dASH;
    }

    @JsonProperty("DASH")
    public void setDASH(CoinsuperBalance dASH) {
      this.dASH = dASH;
    }

    @JsonProperty("DX")
    public CoinsuperBalance getDX() {
      return dX;
    }

    @JsonProperty("DX")
    public void setDX(CoinsuperBalance dX) {
      this.dX = dX;
    }

    @JsonProperty("EOS")
    public CoinsuperBalance getEOS() {
      return eOS;
    }

    @JsonProperty("EOS")
    public void setEOS(CoinsuperBalance eOS) {
      this.eOS = eOS;
    }

    @JsonProperty("ETC")
    public CoinsuperBalance getETC() {
      return eTC;
    }

    @JsonProperty("ETC")
    public void setETC(CoinsuperBalance eTC) {
      this.eTC = eTC;
    }

    @JsonProperty("ETH")
    public CoinsuperBalance getETH() {
      return eTH;
    }

    @JsonProperty("ETH")
    public void setETH(CoinsuperBalance eTH) {
      this.eTH = eTH;
    }

    @JsonProperty("ETK")
    public CoinsuperBalance getETK() {
      return eTK;
    }

    @JsonProperty("ETK")
    public void setETK(CoinsuperBalance eTK) {
      this.eTK = eTK;
    }

    @JsonProperty("HOLD")
    public CoinsuperBalance getHOLD() {
      return hOLD;
    }

    @JsonProperty("HOLD")
    public void setHOLD(CoinsuperBalance hOLD) {
      this.hOLD = hOLD;
    }

    @JsonProperty("IOVC")
    public CoinsuperBalance getIOVC() {
      return iOVC;
    }

    @JsonProperty("IOVC")
    public void setIOVC(CoinsuperBalance iOVC) {
      this.iOVC = iOVC;
    }

    @JsonProperty("LTC")
    public CoinsuperBalance getLTC() {
      return lTC;
    }

    @JsonProperty("LTC")
    public void setLTC(CoinsuperBalance lTC) {
      this.lTC = lTC;
    }

    @JsonProperty("MEDX")
    public CoinsuperBalance getMEDX() {
      return mEDX;
    }

    @JsonProperty("MEDX")
    public void setMEDX(CoinsuperBalance mEDX) {
      this.mEDX = mEDX;
    }

    @JsonProperty("NEO")
    public CoinsuperBalance getNEO() {
      return nEO;
    }

    @JsonProperty("NEO")
    public void setNEO(CoinsuperBalance nEO) {
      this.nEO = nEO;
    }

    @JsonProperty("NEWOS")
    public CoinsuperBalance getNEWOS() {
      return nEWOS;
    }

    @JsonProperty("NEWOS")
    public void setNEWOS(CoinsuperBalance nEWOS) {
      this.nEWOS = nEWOS;
    }

    @JsonProperty("OMG")
    public CoinsuperBalance getOMG() {
      return oMG;
    }

    @JsonProperty("OMG")
    public void setOMG(CoinsuperBalance oMG) {
      this.oMG = oMG;
    }

    @JsonProperty("PRL")
    public CoinsuperBalance getPRL() {
      return pRL;
    }

    @JsonProperty("PRL")
    public void setPRL(CoinsuperBalance pRL) {
      this.pRL = pRL;
    }

    @JsonProperty("QTUM")
    public CoinsuperBalance getQTUM() {
      return qTUM;
    }

    @JsonProperty("QTUM")
    public void setQTUM(CoinsuperBalance qTUM) {
      this.qTUM = qTUM;
    }

    @JsonProperty("RLM")
    public CoinsuperBalance getRLM() {
      return rLM;
    }

    @JsonProperty("RLM")
    public void setRLM(CoinsuperBalance rLM) {
      this.rLM = rLM;
    }

    @JsonProperty("TEST")
    public CoinsuperBalance getTEST() {
      return tEST;
    }

    @JsonProperty("TEST")
    public void setTEST(CoinsuperBalance tEST) {
      this.tEST = tEST;
    }

    @JsonProperty("THRT")
    public CoinsuperBalance getTHRT() {
      return tHRT;
    }

    @JsonProperty("THRT")
    public void setTHRT(CoinsuperBalance tHRT) {
      this.tHRT = tHRT;
    }

    @JsonProperty("USD")
    public CoinsuperBalance getUSD() {
      return uSD;
    }

    @JsonProperty("USD")
    public void setUSD(CoinsuperBalance uSD) {
      this.uSD = uSD;
    }

    @JsonProperty("UST")
    public CoinsuperBalance getUST() {
      return uST;
    }

    @JsonProperty("UST")
    public void setUST(CoinsuperBalance uST) {
      this.uST = uST;
    }

    @JsonProperty("XCCC")
    public CoinsuperBalance getXCCC() {
      return xCCC;
    }

    @JsonProperty("XCCC")
    public void setXCCC(CoinsuperBalance xCCC) {
      this.xCCC = xCCC;
    }

    @JsonProperty("XEM")
    public CoinsuperBalance getXEM() {
      return xEM;
    }

    @JsonProperty("XEM")
    public void setXEM(CoinsuperBalance xEM) {
      this.xEM = xEM;
    }

    @JsonProperty("XRP")
    public CoinsuperBalance getXRP() {
      return xRP;
    }

    @JsonProperty("XRP")
    public void setXRP(CoinsuperBalance xRP) {
      this.xRP = xRP;
    }

    // ---------------------------------------------------
    public static class CoinsuperBalance {

      @JsonProperty("available")
      private BigDecimal available;

      @JsonProperty("total")
      private BigDecimal total;

      public CoinsuperBalance() {
        super();
      }
      /**
       * @param total
       * @param available
       */
      public CoinsuperBalance(BigDecimal available, BigDecimal total) {
        super();
        this.available = available;
        this.total = total;
      }

      @JsonProperty("available")
      public BigDecimal getAvailable() {
        return available;
      }

      @JsonProperty("available")
      public void setAvailable(BigDecimal available) {
        this.available = available;
      }

      @JsonProperty("total")
      public BigDecimal getTotal() {
        return total;
      }

      @JsonProperty("total")
      public void setTotal(BigDecimal total) {
        this.total = total;
      }
    }
  }
}

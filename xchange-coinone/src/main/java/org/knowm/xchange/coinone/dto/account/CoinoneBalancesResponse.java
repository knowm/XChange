package org.knowm.xchange.coinone.dto.account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CoinoneBalancesResponse {

  private final String result;
  private final String errorCode;
  private final CoinoneBalance etc;
  private final CoinoneBalance bch;
  private final CoinoneBalance btc;
  private final CoinoneBalance btg;
  private final CoinoneBalance qtum;
  private final CoinoneBalance iota;
  private final CoinoneBalance omg;
  private final CoinoneBalance eth;
  private final CoinoneBalance krw;
  private final CoinoneBalance xrp;
  private final CoinoneBalance eos;
  private final CoinoneBalance ltc;

  public CoinoneBalancesResponse(
      @JsonProperty("result") String result,
      @JsonProperty("errorCode") String errorCode,
      @JsonProperty("etc") CoinoneBalance etc,
      @JsonProperty("bch") CoinoneBalance bch,
      @JsonProperty("btg") CoinoneBalance btg,
      @JsonProperty("iota") CoinoneBalance iota,
      @JsonProperty("btc") CoinoneBalance btc,
      @JsonProperty("qtum") CoinoneBalance qtum,
      @JsonProperty("eth") CoinoneBalance eth,
      @JsonProperty("omg") CoinoneBalance omg,
      @JsonProperty("krw") CoinoneBalance krw,
      @JsonProperty("xrp") CoinoneBalance xrp,
      @JsonProperty("eos") CoinoneBalance eos,
      @JsonProperty("ltc") CoinoneBalance ltc) {
    this.result = result;
    this.errorCode = errorCode;
    this.etc = etc;
    this.bch = bch;
    this.btc = btc;
    this.btg = btg;
    this.iota = iota;
    this.qtum = qtum;
    this.eth = eth;
    this.omg = omg;
    this.krw = krw;
    this.xrp = xrp;
    this.ltc = ltc;
    this.eos = eos;
  }

  public String getResult() {
    return result;
  }

  public String getErrorCode() {
    return errorCode;
  }

  public CoinoneBalance getEtc() {
    return etc;
  }

  public CoinoneBalance getBch() {
    return bch;
  }

  public CoinoneBalance getBtc() {
    return btc;
  }

  public CoinoneBalance getQtum() {
    return qtum;
  }

  public CoinoneBalance getEth() {
    return eth;
  }

  public CoinoneBalance getKrw() {
    return krw;
  }

  public CoinoneBalance getXrp() {
    return xrp;
  }

  public CoinoneBalance getOmg() {
    return omg;
  }

  public CoinoneBalance getBtg() {
    return btg;
  }

  public CoinoneBalance getIota() {
    return iota;
  }

  public CoinoneBalance getEos() {
    return eos;
  }

  public CoinoneBalance getLtc() {
    return ltc;
  }
}

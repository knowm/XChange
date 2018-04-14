package org.knowm.xchange.coinone.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CoinoneBalancesResponse {

  private final String result;
  private final String errorCode;
  private final CoinoneBalance etc;
  private final CoinoneBalance bch;
  private final CoinoneBalance btc;
  private final CoinoneBalance qtum;
  private final CoinoneBalance eth;
  private final CoinoneBalance krw;
  private final CoinoneBalance xrp;

  public CoinoneBalancesResponse(
      @JsonProperty("result") String result,
      @JsonProperty("errorCode") String errorCode,
      @JsonProperty("etc") CoinoneBalance etc,
      @JsonProperty("bch") CoinoneBalance bch,
      @JsonProperty("btc") CoinoneBalance btc,
      @JsonProperty("qtum") CoinoneBalance qtum,
      @JsonProperty("eth") CoinoneBalance eth,
      @JsonProperty("krw") CoinoneBalance krw,
      @JsonProperty("xrp") CoinoneBalance xrp) {
    this.result = result;
    this.errorCode = errorCode;
    this.etc = etc;
    this.bch = bch;
    this.btc = btc;
    this.qtum = qtum;
    this.eth = eth;
    this.krw = krw;
    this.xrp = xrp;
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
}

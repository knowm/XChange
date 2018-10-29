package org.knowm.xchange.okcoin.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OkCoinFuturesInfoCross {
  private final OkcoinFuturesFundsCross btcFunds;
  private final OkcoinFuturesFundsCross ltcFunds;
  private final OkcoinFuturesFundsCross bchFunds;
  private final OkcoinFuturesFundsCross ethFunds;
  private final OkcoinFuturesFundsCross etcFunds;
  private final OkcoinFuturesFundsCross xrpFunds;
  private final OkcoinFuturesFundsCross eosFunds;
  private final OkcoinFuturesFundsCross btgFunds;

  public OkCoinFuturesInfoCross(
      @JsonProperty("btc") final OkcoinFuturesFundsCross btcFunds,
      @JsonProperty("ltc") final OkcoinFuturesFundsCross ltcFunds,
      @JsonProperty("eth") final OkcoinFuturesFundsCross ethFunds,
      @JsonProperty("etc") final OkcoinFuturesFundsCross etcFunds,
      @JsonProperty("xrp") final OkcoinFuturesFundsCross xrpFunds,
      @JsonProperty("eos") final OkcoinFuturesFundsCross eosFunds,
      @JsonProperty("btg") final OkcoinFuturesFundsCross btgFunds,
      @JsonProperty("bch") final OkcoinFuturesFundsCross bchFunds) {
    this.btcFunds = btcFunds;
    this.ltcFunds = ltcFunds;
    this.bchFunds = bchFunds;
    this.ethFunds = ethFunds;
    this.etcFunds = etcFunds;
    this.xrpFunds = xrpFunds;
    this.eosFunds = eosFunds;
    this.btgFunds = btgFunds;
  }

  public OkcoinFuturesFundsCross getBtcFunds() {
    return btcFunds;
  }

  public OkcoinFuturesFundsCross getLtcFunds() {
    return ltcFunds;
  }

  public OkcoinFuturesFundsCross getEthFunds() {
    return ethFunds;
  }

  public OkcoinFuturesFundsCross getEtcFunds() {
    return etcFunds;
  }

  public OkcoinFuturesFundsCross getXrpFunds() {
    return xrpFunds;
  }

  public OkcoinFuturesFundsCross getEosFunds() {
    return eosFunds;
  }

  public OkcoinFuturesFundsCross getBtgFunds() {
    return btgFunds;
  }

  public OkcoinFuturesFundsCross getBchFunds() {
    return bchFunds;
  }
}

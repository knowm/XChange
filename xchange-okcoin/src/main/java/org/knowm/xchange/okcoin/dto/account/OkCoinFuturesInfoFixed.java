package org.knowm.xchange.okcoin.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OkCoinFuturesInfoFixed {
  private final OkcoinFuturesFundsFixed btcFunds;
  private final OkcoinFuturesFundsFixed ltcFunds;
  private final OkcoinFuturesFundsFixed bchFunds;
  private final OkcoinFuturesFundsFixed ethFunds;
  private final OkcoinFuturesFundsFixed etcFunds;
  private final OkcoinFuturesFundsFixed xrpFunds;
  private final OkcoinFuturesFundsFixed eosFunds;
  private final OkcoinFuturesFundsFixed btgFunds;

  public OkCoinFuturesInfoFixed(
      @JsonProperty("btc") final OkcoinFuturesFundsFixed btcFunds,
      @JsonProperty("ltc") final OkcoinFuturesFundsFixed ltcFunds,
      @JsonProperty("eth") final OkcoinFuturesFundsFixed ethFunds,
      @JsonProperty("etc") final OkcoinFuturesFundsFixed etcFunds,
      @JsonProperty("xrp") final OkcoinFuturesFundsFixed xrpFunds,
      @JsonProperty("eos") final OkcoinFuturesFundsFixed eosFunds,
      @JsonProperty("btg") final OkcoinFuturesFundsFixed btgFunds,
      @JsonProperty("bch") final OkcoinFuturesFundsFixed bchFunds) {
    this.btcFunds = btcFunds;
    this.ltcFunds = ltcFunds;
    this.bchFunds = bchFunds;
    this.ethFunds = ethFunds;
    this.etcFunds = etcFunds;
    this.xrpFunds = xrpFunds;
    this.eosFunds = eosFunds;
    this.btgFunds = btgFunds;
  }

  public OkcoinFuturesFundsFixed getBtcFunds() {
    return btcFunds;
  }

  public OkcoinFuturesFundsFixed getLtcFunds() {
    return ltcFunds;
  }

  public OkcoinFuturesFundsFixed getBchFunds() {
    return bchFunds;
  }

  public OkcoinFuturesFundsFixed getEthFunds() {
    return ethFunds;
  }

  public OkcoinFuturesFundsFixed getEtcFunds() {
    return etcFunds;
  }

  public OkcoinFuturesFundsFixed getXrpFunds() {
    return xrpFunds;
  }

  public OkcoinFuturesFundsFixed getEosFunds() {
    return eosFunds;
  }

  public OkcoinFuturesFundsFixed getBtgFunds() {
    return btgFunds;
  }
}

package org.knowm.xchange.okcoin.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OkCoinFuturesInfoCross {
  private final OkcoinFuturesFundsCross btcFunds;
  private final OkcoinFuturesFundsCross ltcFunds;
  private final OkcoinFuturesFundsCross bchFunds;

  public OkCoinFuturesInfoCross(
      @JsonProperty("btc") final OkcoinFuturesFundsCross btcFunds,
      @JsonProperty("ltc") final OkcoinFuturesFundsCross ltcFunds,
      @JsonProperty("bch") final OkcoinFuturesFundsCross bchFunds) {
    this.btcFunds = btcFunds;
    this.ltcFunds = ltcFunds;
    this.bchFunds = bchFunds;
  }

  public OkcoinFuturesFundsCross getBtcFunds() {
    return btcFunds;
  }

  public OkcoinFuturesFundsCross getLtcFunds() {
    return ltcFunds;
  }

  public OkcoinFuturesFundsCross getBchFunds() {
    return bchFunds;
  }
}

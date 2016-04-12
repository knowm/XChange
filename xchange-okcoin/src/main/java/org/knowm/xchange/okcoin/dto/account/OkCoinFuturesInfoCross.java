package org.knowm.xchange.okcoin.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OkCoinFuturesInfoCross {
  private final OkcoinFuturesFundsCross btcFunds;
  private final OkcoinFuturesFundsCross ltcFunds;

  public OkCoinFuturesInfoCross(@JsonProperty("btc") final OkcoinFuturesFundsCross btcFunds,
      @JsonProperty("ltc") final OkcoinFuturesFundsCross ltcFunds) {
    this.btcFunds = btcFunds;
    this.ltcFunds = ltcFunds;
  }

  public OkcoinFuturesFundsCross getBtcFunds() {
    return btcFunds;
  }

  public OkcoinFuturesFundsCross getLtcFunds() {
    return ltcFunds;
  }

}

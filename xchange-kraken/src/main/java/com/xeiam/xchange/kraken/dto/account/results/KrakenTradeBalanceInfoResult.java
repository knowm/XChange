package com.xeiam.xchange.kraken.dto.account.results;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.kraken.dto.KrakenResult;
import com.xeiam.xchange.kraken.dto.account.KrakenTradeBalanceInfo;

public class KrakenTradeBalanceInfoResult extends KrakenResult<KrakenTradeBalanceInfo> {

  /**
   * Constructor
   * 
   * @param result
   * @param error
   */
  public KrakenTradeBalanceInfoResult(@JsonProperty("result") KrakenTradeBalanceInfo result, @JsonProperty("error") String[] error) {

    super(result, error);
  }

}

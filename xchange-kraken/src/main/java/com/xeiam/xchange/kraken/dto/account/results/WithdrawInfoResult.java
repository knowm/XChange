package com.xeiam.xchange.kraken.dto.account.results;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.kraken.dto.KrakenResult;
import com.xeiam.xchange.kraken.dto.account.WithdrawInfo;

public class WithdrawInfoResult extends KrakenResult<WithdrawInfo> {

  public WithdrawInfoResult(@JsonProperty("result") WithdrawInfo result, @JsonProperty("error") String[] error) {
    super(result, error);
  }

}

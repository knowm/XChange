package org.knowm.xchange.kraken.dto.account.results;

import org.knowm.xchange.kraken.dto.KrakenResult;
import org.knowm.xchange.kraken.dto.account.WithdrawInfo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WithdrawInfoResult extends KrakenResult<WithdrawInfo> {

  public WithdrawInfoResult(@JsonProperty("result") WithdrawInfo result, @JsonProperty("error") String[] error) {
    super(result, error);
  }

}

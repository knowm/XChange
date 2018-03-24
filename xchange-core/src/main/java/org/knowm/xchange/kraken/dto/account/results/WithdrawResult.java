package org.knowm.xchange.kraken.dto.account.results;

import org.knowm.xchange.kraken.dto.KrakenResult;
import org.knowm.xchange.kraken.dto.account.Withdraw;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WithdrawResult extends KrakenResult<Withdraw> {

  public WithdrawResult(@JsonProperty("result") Withdraw result, @JsonProperty("error") String[] error) {
    super(result, error);
  }

}

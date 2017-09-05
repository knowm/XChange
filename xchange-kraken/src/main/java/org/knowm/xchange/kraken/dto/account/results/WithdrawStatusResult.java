package org.knowm.xchange.kraken.dto.account.results;

import java.util.List;

import org.knowm.xchange.kraken.dto.KrakenResult;
import org.knowm.xchange.kraken.dto.account.WithdrawStatus;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WithdrawStatusResult extends KrakenResult<List<WithdrawStatus>> {

  public WithdrawStatusResult(@JsonProperty("result") List<WithdrawStatus> result, @JsonProperty("error") String[] error) {
    super(result, error);
  }

}

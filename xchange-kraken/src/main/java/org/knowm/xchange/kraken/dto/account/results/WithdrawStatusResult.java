package org.knowm.xchange.kraken.dto.account.results;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import org.knowm.xchange.kraken.dto.KrakenResult;
import org.knowm.xchange.kraken.dto.account.WithdrawStatus;

public class WithdrawStatusResult extends KrakenResult<List<WithdrawStatus>> {

  public WithdrawStatusResult(
      @JsonProperty("result") List<WithdrawStatus> result, @JsonProperty("error") String[] error) {
    super(result, error);
  }
}

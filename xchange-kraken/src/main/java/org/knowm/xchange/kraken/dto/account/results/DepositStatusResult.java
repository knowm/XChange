package org.knowm.xchange.kraken.dto.account.results;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import org.knowm.xchange.kraken.dto.KrakenResult;
import org.knowm.xchange.kraken.dto.account.DepostitStatus;

public class DepositStatusResult extends KrakenResult<List<DepostitStatus>> {

  public DepositStatusResult(
      @JsonProperty("result") List<DepostitStatus> result, @JsonProperty("error") String[] error) {
    super(result, error);
  }
}

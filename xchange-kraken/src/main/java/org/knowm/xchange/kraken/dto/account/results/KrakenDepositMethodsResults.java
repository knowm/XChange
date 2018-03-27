package org.knowm.xchange.kraken.dto.account.results;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.kraken.dto.KrakenResult;
import org.knowm.xchange.kraken.dto.account.KrakenDepositMethods;

public class KrakenDepositMethodsResults extends KrakenResult<KrakenDepositMethods[]> {

  public KrakenDepositMethodsResults(
      @JsonProperty("result") KrakenDepositMethods[] result,
      @JsonProperty("error") String[] error) {
    super(result, error);
  }
}

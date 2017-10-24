package org.knowm.xchange.kraken.dto.account.results;

import org.knowm.xchange.kraken.dto.KrakenResult;
import org.knowm.xchange.kraken.dto.account.KrakenDepositMethods;

import com.fasterxml.jackson.annotation.JsonProperty;

public class KrakenDepositMethodsResults extends KrakenResult<KrakenDepositMethods[]> {

  public KrakenDepositMethodsResults(@JsonProperty("result") KrakenDepositMethods[] result, @JsonProperty("error") String[] error) {
    super(result, error);
  }
}

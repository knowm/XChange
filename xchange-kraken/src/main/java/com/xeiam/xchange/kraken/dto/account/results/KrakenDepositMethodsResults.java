package com.xeiam.xchange.kraken.dto.account.results;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.kraken.dto.KrakenResult;
import com.xeiam.xchange.kraken.dto.account.KrakenDepositMethods;

public class KrakenDepositMethodsResults extends KrakenResult<KrakenDepositMethods[]> {

  public KrakenDepositMethodsResults(@JsonProperty("result") KrakenDepositMethods[] result, @JsonProperty("error") String[] error) {
    super(result, error);
  }
}
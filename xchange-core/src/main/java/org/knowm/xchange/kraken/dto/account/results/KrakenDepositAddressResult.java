package org.knowm.xchange.kraken.dto.account.results;

import org.knowm.xchange.kraken.dto.KrakenResult;
import org.knowm.xchange.kraken.dto.account.KrakenDepositAddress;

import com.fasterxml.jackson.annotation.JsonProperty;

public class KrakenDepositAddressResult extends KrakenResult<KrakenDepositAddress[]> {

  public KrakenDepositAddressResult(@JsonProperty("result") KrakenDepositAddress[] result, @JsonProperty("error") String[] error) {
    super(result, error);
  }

}

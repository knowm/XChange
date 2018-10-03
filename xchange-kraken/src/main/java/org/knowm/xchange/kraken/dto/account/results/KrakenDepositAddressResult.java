package org.knowm.xchange.kraken.dto.account.results;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.kraken.dto.KrakenResult;
import org.knowm.xchange.kraken.dto.account.KrakenDepositAddress;

public class KrakenDepositAddressResult extends KrakenResult<KrakenDepositAddress[]> {

  public KrakenDepositAddressResult(
      @JsonProperty("result") KrakenDepositAddress[] result,
      @JsonProperty("error") String[] error) {
    super(result, error);
  }
}

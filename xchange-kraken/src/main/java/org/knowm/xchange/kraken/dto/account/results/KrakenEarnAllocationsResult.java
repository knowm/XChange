package org.knowm.xchange.kraken.dto.account.results;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.kraken.dto.KrakenResult;
import org.knowm.xchange.kraken.dto.account.KrakenEarnAllocations;

public class KrakenEarnAllocationsResult extends KrakenResult<KrakenEarnAllocations> {

  public KrakenEarnAllocationsResult(
      @JsonProperty("result") KrakenEarnAllocations result, @JsonProperty("error") String[] error) {
    super(result, error);
  }
}

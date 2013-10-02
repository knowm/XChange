package org.xchange.kraken.dto.trade;

import org.xchange.kraken.dto.KrakenResult;

import com.fasterxml.jackson.annotation.JsonProperty;

public class KrakenOpenOrdersResult extends KrakenResult<KrakenOuterOpen> {

  public KrakenOpenOrdersResult(@JsonProperty("result") KrakenOuterOpen result, @JsonProperty("error") String[] error) {

    super(result, error);
  }
}

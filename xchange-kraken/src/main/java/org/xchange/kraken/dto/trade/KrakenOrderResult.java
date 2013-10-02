package org.xchange.kraken.dto.trade;

import org.xchange.kraken.dto.KrakenResult;

import com.fasterxml.jackson.annotation.JsonProperty;

public class KrakenOrderResult extends KrakenResult<KrakenOrderReturn> {

  public KrakenOrderResult(@JsonProperty("result") KrakenOrderReturn result, @JsonProperty("error") String[] error) {

    super(result, error);
  }
}

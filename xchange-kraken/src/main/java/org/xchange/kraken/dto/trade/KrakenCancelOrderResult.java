package org.xchange.kraken.dto.trade;

import org.xchange.kraken.dto.KrakenResult;

import com.fasterxml.jackson.annotation.JsonProperty;


public class KrakenCancelOrderResult extends KrakenResult<KrakenCancelCount> {
  public KrakenCancelOrderResult(@JsonProperty("result") KrakenCancelCount result, @JsonProperty("error") String[] error) {

    super(result, error);
  }
}

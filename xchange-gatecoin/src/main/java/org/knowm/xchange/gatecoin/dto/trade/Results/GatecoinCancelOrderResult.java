package org.knowm.xchange.gatecoin.dto.trade.Results;

import org.knowm.xchange.gatecoin.dto.GatecoinResult;
import org.knowm.xchange.gatecoin.dto.marketdata.ResponseStatus;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author sumedha
 */
public class GatecoinCancelOrderResult extends GatecoinResult {

  @JsonCreator
  public GatecoinCancelOrderResult(@JsonProperty("responseStatus") ResponseStatus responseStatus) {
    super(responseStatus);
  }
}

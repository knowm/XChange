package org.knowm.xchange.gatecoin.dto.trade.Results;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.gatecoin.dto.GatecoinResult;
import org.knowm.xchange.gatecoin.dto.marketdata.ResponseStatus;

/** @author sumedha */
public class GatecoinPlaceOrderResult extends GatecoinResult {
  private final String clOrderId;

  @JsonCreator
  public GatecoinPlaceOrderResult(
      @JsonProperty("clOrderId") String clOrderId,
      @JsonProperty("responseStatus") ResponseStatus responseStatus) {
    super(responseStatus);
    this.clOrderId = clOrderId;
  }

  public String getOrderId() {
    return clOrderId;
  }
}

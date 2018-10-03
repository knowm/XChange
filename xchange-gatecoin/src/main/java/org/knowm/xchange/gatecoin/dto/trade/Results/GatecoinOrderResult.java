package org.knowm.xchange.gatecoin.dto.trade.Results;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.gatecoin.dto.GatecoinResult;
import org.knowm.xchange.gatecoin.dto.marketdata.ResponseStatus;
import org.knowm.xchange.gatecoin.dto.trade.GatecoinOrder;

/** @author sumedha */
public class GatecoinOrderResult extends GatecoinResult {

  private final GatecoinOrder[] orders;

  @JsonCreator
  public GatecoinOrderResult(
      @JsonProperty("orders") GatecoinOrder[] orders,
      @JsonProperty("responseStatus") ResponseStatus responseStatus) {
    super(responseStatus);
    this.orders = orders;
  }

  public GatecoinOrder[] getOrders() {
    return orders;
  }
}

package org.knowm.xchange.lakebtc.dto.trade;

import org.knowm.xchange.lakebtc.dto.LakeBTCRequest;

/** Created by cristian.lucaci on 12/19/2014. */
public class LakeBTCOrdersRequest extends LakeBTCRequest {

  private static final String METHOD_NAME = "getOrders";

  /** Constructor */
  public LakeBTCOrdersRequest() {

    method = METHOD_NAME;
    params = "[]";
  }

  public LakeBTCOrdersRequest(String type) {

    method = METHOD_NAME;
    params = String.format("[\"%s\"]", type);
  }

  @Override
  public String toString() {
    return String.format("LakeBTCOrderRequest{id=%d, method=%s, params=%s}", id, method, params);
  }
}

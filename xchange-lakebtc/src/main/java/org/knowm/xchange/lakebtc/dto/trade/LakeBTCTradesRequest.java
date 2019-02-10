package org.knowm.xchange.lakebtc.dto.trade;

import org.knowm.xchange.lakebtc.dto.LakeBTCRequest;

/** Created by cristian.lucaci on 12/19/2014. */
public class LakeBTCTradesRequest extends LakeBTCRequest {

  private static final String METHOD_NAME = "getTrades";

  /** Constructor */
  public LakeBTCTradesRequest() {

    method = METHOD_NAME;
    params = "[]";
  }

  public LakeBTCTradesRequest(String type) {

    method = METHOD_NAME;
    params = String.format("[\"%s\"]", type);
  }

  @Override
  public String toString() {
    return String.format("LakeBTCCancelRequest{id=%d, method=%s, params=%s}", id, method, params);
  }
}

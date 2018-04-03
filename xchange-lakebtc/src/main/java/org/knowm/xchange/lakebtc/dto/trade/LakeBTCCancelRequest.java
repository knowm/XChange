package org.knowm.xchange.lakebtc.dto.trade;

import org.knowm.xchange.lakebtc.dto.LakeBTCRequest;

/** Created by cristian.lucaci on 12/19/2014. */
public class LakeBTCCancelRequest extends LakeBTCRequest {

  private static final String METHOD_NAME = "cancelOrder";

  /** Constructor */
  public LakeBTCCancelRequest() {

    method = METHOD_NAME;
    params = "[]";
  }

  public LakeBTCCancelRequest(String type) {

    method = METHOD_NAME;
    params = String.format("[\"%s\"]", type);
  }

  @Override
  public String toString() {
    return String.format("LakeBTCCancelRequest{id=%d, method=%s, params=%s}", id, method, params);
  }
}

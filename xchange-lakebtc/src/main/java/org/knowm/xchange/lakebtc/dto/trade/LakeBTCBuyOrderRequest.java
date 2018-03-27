package org.knowm.xchange.lakebtc.dto.trade;

import org.knowm.xchange.lakebtc.dto.LakeBTCRequest;

/** Created by cristian.lucaci on 12/19/2014. */
public class LakeBTCBuyOrderRequest extends LakeBTCRequest {

  private static final String METHOD_NAME = "buyOrder";

  /** Constructor */
  public LakeBTCBuyOrderRequest() {

    method = METHOD_NAME;
    params = "[]";
  }

  public LakeBTCBuyOrderRequest(String type) {

    method = METHOD_NAME;
    params = String.format("[\"%s\"]", type);
  }

  @Override
  public String toString() {
    return String.format("LakeBTCBuyOrderRequest{id=%d, method=%s, params=%s}", id, method, params);
  }
}

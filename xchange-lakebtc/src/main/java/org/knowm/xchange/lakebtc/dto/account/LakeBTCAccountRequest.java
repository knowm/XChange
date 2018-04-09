package org.knowm.xchange.lakebtc.dto.account;

import org.knowm.xchange.lakebtc.dto.LakeBTCRequest;

/** User: cristian.lucaci Date: 10/3/2014 Time: 5:37 PM */
public class LakeBTCAccountRequest extends LakeBTCRequest {

  private static final String METHOD_NAME = "getAccountInfo";

  /** Constructor */
  public LakeBTCAccountRequest() {

    method = METHOD_NAME;
    params = "[]";
  }

  public LakeBTCAccountRequest(String type) {

    method = METHOD_NAME;
    params = String.format("[\"%s\"]", type);
  }

  @Override
  public String toString() {
    return String.format("LakeBTCAccountRequest{id=%d, method=%s, params=%s}", id, method, params);
  }
}

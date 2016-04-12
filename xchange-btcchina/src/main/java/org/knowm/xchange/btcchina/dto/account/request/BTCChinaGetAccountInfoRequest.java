package org.knowm.xchange.btcchina.dto.account.request;

import org.knowm.xchange.btcchina.dto.BTCChinaRequest;

/**
 * @author David Yam
 */
public final class BTCChinaGetAccountInfoRequest extends BTCChinaRequest {

  public static final String ALL_TYPE = "all";
  public static final String BALANCE_TYPE = "balance";
  public static final String FROZEN_TYPE = "frozen";
  public static final String PROFILE_TYPE = "profile";

  private static final String METHOD_NAME = "getAccountInfo";

  /**
   * Constructor
   */
  public BTCChinaGetAccountInfoRequest() {

    method = METHOD_NAME;
    params = "[]";
  }

  public BTCChinaGetAccountInfoRequest(String type) {

    method = METHOD_NAME;
    params = String.format("[\"%s\"]", type);
  }

  @Override
  public String toString() {

    return String.format("BTCChinaGetAccountInfoRequest{id=%d, method=%s, params=%s}", id, method, params);
  }

}

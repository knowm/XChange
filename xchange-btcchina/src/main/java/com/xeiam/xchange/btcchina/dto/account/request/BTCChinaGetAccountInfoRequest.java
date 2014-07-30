package com.xeiam.xchange.btcchina.dto.account.request;

import com.xeiam.xchange.btcchina.dto.BTCChinaRequest;

/**
 * @author David Yam
 */
public final class BTCChinaGetAccountInfoRequest extends BTCChinaRequest {

  /**
   * Constructor
   */
  public BTCChinaGetAccountInfoRequest() {

    method = "getAccountInfo";
    params = "[]";
  }

  @Override
  public String toString() {

    return String.format("BTCChinaGetAccountInfoRequest{id=%d, method=%s, params=%s}", id, method, params);
  }

}

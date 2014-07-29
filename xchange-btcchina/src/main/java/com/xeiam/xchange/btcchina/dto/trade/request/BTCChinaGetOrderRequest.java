package com.xeiam.xchange.btcchina.dto.trade.request;

import com.xeiam.xchange.btcchina.dto.BTCChinaRequest;

/**
 * @author Joe Zhou
 */
public final class BTCChinaGetOrderRequest extends BTCChinaRequest {

  private static final String METHOD_NAME = "getOrder";

  public BTCChinaGetOrderRequest(int id) {

    method = METHOD_NAME;
    params = "[" + id + "]";
  }

  /**
   * @deprecated
   */
  @Deprecated
  public BTCChinaGetOrderRequest(long id) {
    this((int) id);
  }

  public BTCChinaGetOrderRequest(int id, String market) {

    method = METHOD_NAME;
    params = String.format("[%1$d,\"%2$s\"]", id, market);
  }

  /**
   * @deprecated
   */
  @Deprecated
  public BTCChinaGetOrderRequest(long id, String market) {
    this((int) id, market);
  }

  @Override
  public String toString() {

    return String.format("BTCChinaGetOrderRequest{id=%d, method=%s, params=%s}", id, method, params);
  }

}

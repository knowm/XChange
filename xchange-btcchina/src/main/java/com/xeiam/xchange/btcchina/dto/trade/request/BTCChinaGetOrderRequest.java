package com.xeiam.xchange.btcchina.dto.trade.request;

import com.xeiam.xchange.btcchina.dto.BTCChinaRequest;

/**
 * @author Joe Zhou
 */
public final class BTCChinaGetOrderRequest extends BTCChinaRequest {

  private static final String METHOD_NAME = "getOrder";

  public BTCChinaGetOrderRequest(long orderId) {
    method = METHOD_NAME;
    params = "[" + orderId + "]";
  }

  public BTCChinaGetOrderRequest(long orderId, String market) {
    method = METHOD_NAME;
    params = String.format("[%1$d,\"%2$s\"]", orderId, market);
  }

  @Override
  public String toString() {
    return String.format("BTCChinaGetOrderRequest{id=%d, method=%s, params=%s}", id, method, params);
  }

}

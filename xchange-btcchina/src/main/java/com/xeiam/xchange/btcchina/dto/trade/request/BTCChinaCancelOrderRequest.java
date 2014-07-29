package com.xeiam.xchange.btcchina.dto.trade.request;

import com.xeiam.xchange.btcchina.dto.BTCChinaRequest;

/**
 * @author David Yam
 */
public final class BTCChinaCancelOrderRequest extends BTCChinaRequest {

  /**
   * Constructor
   */
  public BTCChinaCancelOrderRequest(int id) {

    method = "cancelOrder";
    params = "[" + id + "]";
  }

  /**
   * @deprecated
   */
  @Deprecated
  public BTCChinaCancelOrderRequest(long id) {
    this((int) id);
  }

  @Override
  public String toString() {

    return String.format("BTCChinaCancelOrderRequest{id=%d, method=%s, params=%s}", id, method, params);
  }

}

package com.xeiam.xchange.btcchina.dto.trade.request;

import com.xeiam.xchange.btcchina.dto.BTCChinaRequest;

/**
 * Request for {@code getOrder}.
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

  /**
   * Constructs a {@code getOrder} request.
   * 
   * @param id the order ID.
   * @param market Default to “BTCCNY”. [ BTCCNY | LTCCNY | LTCBTC ]
   * @param withdetail return the trade details or not for this order. Default to false, no detail will be returned.
   */
  public BTCChinaGetOrderRequest(int id, String market, Boolean withdetail) {

    method = METHOD_NAME;
    params = String.format("[%d,\"%s\",%b]", id, market, withdetail);
  }

  @Override
  public String toString() {

    return String.format("BTCChinaGetOrderRequest{id=%d, method=%s, params=%s}", id, method, params);
  }

}

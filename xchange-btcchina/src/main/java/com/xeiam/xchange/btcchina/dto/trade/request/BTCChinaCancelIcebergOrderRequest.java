package com.xeiam.xchange.btcchina.dto.trade.request;

public class BTCChinaCancelIcebergOrderRequest extends BTCChinaCancelOrderRequest {

  private static final String METHOD_NAME = "cancelIcebergOrder";

  public BTCChinaCancelIcebergOrderRequest(int id, String market) {

    super(METHOD_NAME, id, market);
  }

  /**
   * @deprecated
   */
  @Deprecated
  public BTCChinaCancelIcebergOrderRequest(long id, String market) {

    this((int) id, market);
  }

}

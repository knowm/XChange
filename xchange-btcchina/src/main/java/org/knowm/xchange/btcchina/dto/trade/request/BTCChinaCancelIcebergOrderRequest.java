package org.knowm.xchange.btcchina.dto.trade.request;

public class BTCChinaCancelIcebergOrderRequest extends BTCChinaCancelOrderRequest {

  private static final String METHOD_NAME = "cancelIcebergOrder";

  /**
   * Constructor
   *
   * @param id
   * @param market
   */
  public BTCChinaCancelIcebergOrderRequest(int id, String market) {

    super(METHOD_NAME, id, market);
  }

}

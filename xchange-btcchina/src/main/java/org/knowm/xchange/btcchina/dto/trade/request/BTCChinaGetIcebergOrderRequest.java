package org.knowm.xchange.btcchina.dto.trade.request;

public class BTCChinaGetIcebergOrderRequest extends BTCChinaGetOrderRequest {

  private static final String METHOD_NAME = "getIcebergOrder";

  public BTCChinaGetIcebergOrderRequest(int id, String market) {

    super(METHOD_NAME, id, market);
  }

}

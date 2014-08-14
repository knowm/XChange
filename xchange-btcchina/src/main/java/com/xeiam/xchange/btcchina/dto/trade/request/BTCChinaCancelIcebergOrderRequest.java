package com.xeiam.xchange.btcchina.dto.trade.request;

import com.xeiam.xchange.btcchina.BTCChinaExchange;
import com.xeiam.xchange.btcchina.dto.BTCChinaRequest;

public class BTCChinaCancelIcebergOrderRequest extends BTCChinaRequest {

  private static final String METHOD_NAME = "cancelIcebergOrder";

  public BTCChinaCancelIcebergOrderRequest(int id, String market) {

    this.method = METHOD_NAME;
    this.params = String.format("[%1$d,\"%2$s\"]", id, market == null ? BTCChinaExchange.DEFAULT_MARKET : market);
  }

  /**
   * @deprecated
   */
  @Deprecated
  public BTCChinaCancelIcebergOrderRequest(long id, String market) {

    this((int) id, market);
  }

}

package org.knowm.xchange.btcchina.dto.trade.request;

import org.knowm.xchange.btcchina.BTCChinaExchange;
import org.knowm.xchange.btcchina.dto.BTCChinaRequest;

/**
 * @author David Yam
 */
public class BTCChinaCancelOrderRequest extends BTCChinaRequest {

  /**
   * Constructor
   *
   * @param method
   * @param id
   * @param market
   */
  public BTCChinaCancelOrderRequest(String method, int id, String market) {

    this.method = method;
    this.params = String.format("[%1$d,\"%2$s\"]", id, market == null ? BTCChinaExchange.DEFAULT_MARKET : market);
  }

  /**
   * Constructor
   */
  public BTCChinaCancelOrderRequest(int id) {

    method = "cancelOrder";
    params = "[" + id + "]";
  }

  @Override
  public String toString() {

    return String.format("BTCChinaCancelOrderRequest{id=%d, method=%s, params=%s}", id, method, params);
  }

}

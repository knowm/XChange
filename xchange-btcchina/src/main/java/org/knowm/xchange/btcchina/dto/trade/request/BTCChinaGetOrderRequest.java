package org.knowm.xchange.btcchina.dto.trade.request;

import org.knowm.xchange.btcchina.BTCChinaExchange;
import org.knowm.xchange.btcchina.dto.BTCChinaRequest;

/**
 * Request for {@code getOrder}.
 */
public class BTCChinaGetOrderRequest extends BTCChinaRequest {

  private static final String METHOD_NAME = "getOrder";

  public BTCChinaGetOrderRequest(String method, int id, String market) {

    this.method = method;
    this.params = String.format("[%1$d,\"%2$s\"]", id, market == null ? BTCChinaExchange.DEFAULT_MARKET : market);
  }

  public BTCChinaGetOrderRequest(int id) {

    method = METHOD_NAME;
    params = "[" + id + "]";
  }

  public BTCChinaGetOrderRequest(int id, String market) {

    method = METHOD_NAME;
    params = String.format("[%1$d,\"%2$s\"]", id, market);
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

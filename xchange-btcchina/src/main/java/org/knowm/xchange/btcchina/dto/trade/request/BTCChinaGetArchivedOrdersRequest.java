package org.knowm.xchange.btcchina.dto.trade.request;

import org.knowm.xchange.btcchina.BTCChinaExchange;
import org.knowm.xchange.btcchina.dto.BTCChinaRequest;

public final class BTCChinaGetArchivedOrdersRequest extends BTCChinaRequest {

  public static final int DEFAULT_LIMIT = 200;

  private static final String METHOD_NAME = "getArchivedOrders";

  /**
   * Constructs a {@code getArchivedOrders} request.
   *
   * @param market Default to "BTCCNY". [ BTCCNY | LTCCNY | LTCBTC | ALL]
   * @param limit Limit the number of transactions, default value is 200.
   * @param lessThanOrderId Start index used for, default value is 0 which means the max order_id in db.
   * @param withdetail Return the trade details or not for this order. Default to false, no detail will be returned.
   */
  public BTCChinaGetArchivedOrdersRequest(String market, Integer limit, Integer lessThanOrderId, Boolean withdetail) {

    method = METHOD_NAME;
    params = String.format("[\"%s\",%d,%d,%b]", market == null ? BTCChinaExchange.DEFAULT_MARKET : market, limit == null ? DEFAULT_LIMIT : limit.intValue(),
        lessThanOrderId == null ? 0 : lessThanOrderId.intValue(), withdetail);
  }

  @Override
  public String toString() {

    return String.format("BTCChinaGetArchivedOrdersRequest{id=%d, method=%s, params=%s}", id, method, params);
  }

}

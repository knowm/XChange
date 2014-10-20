package com.xeiam.xchange.btcchina.dto.trade.request;

import com.xeiam.xchange.btcchina.BTCChinaExchange;
import com.xeiam.xchange.btcchina.dto.BTCChinaRequest;

/**
 * @author David Yam
 */
public final class BTCChinaGetOrdersRequest extends BTCChinaRequest {

  /**
   * @deprecated
   */
  @Deprecated
  public static final String DEFAULT_MARKET = BTCChinaExchange.DEFAULT_MARKET;
  public static final String ALL_MARKET = "ALL";

  public static final int DEFAULT_LIMIT = 1000;

  private static final String METHOD_NAME = "getOrders";

  /**
   * Constructor (Optional parameter, default openOnly = false)
   * 
   * @deprecated Use {@link #BTCChinaGetOrdersRequest(Boolean, String, Integer, Integer)} instead.
   */
  @Deprecated
  public BTCChinaGetOrdersRequest() {

    method = METHOD_NAME;
    params = "[]";
  }

  /**
   * Constructor
   * 
   * @param openOnly
   * @deprecated this constructor is incorrect,
   *             it will fail into `invalid parameter'(error code = -32019).
   */
  @Deprecated
  public BTCChinaGetOrdersRequest(Boolean openOnly) {

    method = METHOD_NAME;
    params = "[" + ((openOnly) ? 1 : 0) + "]";
  }

  /**
   * Constructor.
   *
   * @param openOnly Default is 'true'. Only open orders are returned.
   * @param market Default to "BTCCNY". [ BTCCNY | LTCCNY | LTCBTC | ALL]
   * @param limit Limit the number of transactions, default value is 1000.
   * @param offset Start index used for pagination, default value is 0.
   */
  public BTCChinaGetOrdersRequest(Boolean openOnly, String market, Integer limit, Integer offset) {

    method = METHOD_NAME;
    params =
        String.format("[%1$s,\"%2$s\",%3$d,%4$d]", openOnly == null ? true : openOnly.booleanValue(), market == null ? BTCChinaExchange.DEFAULT_MARKET : market, limit == null ? DEFAULT_LIMIT : limit
            .intValue(), offset == null ? 0 : offset.intValue());
  }

  /**
   * Constructs a {@code getOrders} request.
   * 
   * @param openOnly Default is 'true'. Only open orders are returned.
   * @param market Default to "BTCCNY". [ BTCCNY | LTCCNY | LTCBTC | ALL]
   * @param limit Limit the number of transactions, default value is 1000.
   * @param offset Start index used for pagination, default value is 0.
   * @param since Define the starting time from when the orders are fetched.
   * @param withdetail Return the trade details or not for this order. Default to false, no detail will be returned.
   */
  public BTCChinaGetOrdersRequest(Boolean openOnly, String market, Integer limit, Integer offset, Integer since, Boolean withdetail) {

    method = METHOD_NAME;
    params =
        String.format("[%b,\"%s\",%d,%d,%d,%b]", openOnly == null ? true : openOnly.booleanValue(), market == null ? BTCChinaExchange.DEFAULT_MARKET : market, limit == null ? DEFAULT_LIMIT : limit
            .intValue(), offset == null ? 0 : offset.intValue(), since == null ? 0 : since.intValue(), withdetail);
  }

  @Override
  public String toString() {

    return String.format("BTCChinaGetOrdersRequest{id=%d, method=%s, params=%s}", id, method, params);
  }

}

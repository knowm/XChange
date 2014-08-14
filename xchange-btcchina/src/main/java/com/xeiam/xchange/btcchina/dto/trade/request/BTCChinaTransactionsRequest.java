package com.xeiam.xchange.btcchina.dto.trade.request;

import com.xeiam.xchange.btcchina.dto.BTCChinaRequest;

public class BTCChinaTransactionsRequest extends BTCChinaRequest {

  public static final String TYPE_ALL = "all";

  /**
   * Constructor
   */
  public BTCChinaTransactionsRequest() {

    method = "getTransactions";
    params = "[]";
  }

  /**
   * Constructs a getting transactions log request.
   *
   * @param type Fetch transactions by type.
   *          Default is 'all'.
   *          Available types 'all | fundbtc | withdrawbtc | fundmoney | withdrawmoney
   *          | refundmoney | buybtc | sellbtc | buyltc | sellltc | tradefee | rebate '
   * @param limit Limit the number of transactions, default value is 10.
   * @param offset Start index used for pagination, default value is 0.
   */
  public BTCChinaTransactionsRequest(String type, Integer limit, Integer offset) {

    method = "getTransactions";
    params = String.format("[\"%1$s\",%2$d,%3$d]", type == null ? TYPE_ALL : type, limit == null ? 10 : limit.intValue(), offset == null ? 0 : offset.intValue());
  }

  @Override
  public String toString() {

    return String.format("BTCChinaTransactionsRequest{id=%d, method=%s, params=%s}", id, method, params);
  }
}

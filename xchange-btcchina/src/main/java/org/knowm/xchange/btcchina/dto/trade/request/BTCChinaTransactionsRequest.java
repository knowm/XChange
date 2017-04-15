package org.knowm.xchange.btcchina.dto.trade.request;

import org.knowm.xchange.btcchina.dto.BTCChinaRequest;

public class BTCChinaTransactionsRequest extends BTCChinaRequest {

  public static final String TYPE_ALL = "all";
  public static final String SINCE_TIME = "time";
  public static final String SINCE_ID = "id";

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
   * @param type Fetch transactions by type. Default is 'all'. Available types 'all | fundbtc | withdrawbtc | fundmoney | withdrawmoney | refundmoney
   * | buybtc | sellbtc | buyltc | sellltc | tradefee | rebate '
   * @param limit Limit the number of transactions, default value is 10.
   * @param offset Start index used for pagination, default value is 0.
   * @param since To fetch the transactions from this point, which can either be an order id or a unix timestamp, default value is 0.
   * @param sincetype Specify the type of 'since' parameter, can either be 'id' or 'time'. default value is 'time'.
   */
  public BTCChinaTransactionsRequest(String type, Integer limit, Integer offset, Integer since, String sincetype) {

    method = "getTransactions";
    params = String.format("[\"%s\",%d,%d,%d,\"%s\"]", type == null ? TYPE_ALL : type, limit == null ? 10 : limit.intValue(),
        offset == null ? 0 : offset.intValue(), since == null ? 0 : since.intValue(), sincetype == null ? "time" : sincetype);
  }

  @Override
  public String toString() {

    return String.format("BTCChinaTransactionsRequest{id=%d, method=%s, params=%s}", id, method, params);
  }
}

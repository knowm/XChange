package com.xeiam.xchange.btcchina.dto.trade.request;

import com.xeiam.xchange.btcchina.dto.BTCChinaRequest;

public class BTCChinaTransactionsRequest extends BTCChinaRequest {

  /**
   * Constructor
   * 
   * @param orderId
   */
  public BTCChinaTransactionsRequest() {

    method = "getTransactions";
    params = "[]";
  }

  @Override
  public String toString() {

    return String.format("BTCChinaTransactionsRequest{id=%d, method=%s, params=%s}", id, method, params);
  }
}

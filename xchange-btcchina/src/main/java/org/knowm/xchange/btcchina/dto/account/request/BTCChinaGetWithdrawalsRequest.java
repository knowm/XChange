package org.knowm.xchange.btcchina.dto.account.request;

import org.knowm.xchange.btcchina.dto.BTCChinaRequest;

public class BTCChinaGetWithdrawalsRequest extends BTCChinaRequest {

  private static final String METHOD_NAME = "getWithdrawals";

  /**
   * @param currency [ BTC | LTC ]
   * @param pendingOnly only pending withdrawals are returned if true.
   */
  public BTCChinaGetWithdrawalsRequest(String currency, boolean pendingOnly) {

    method = METHOD_NAME;
    params = String.format("[\"%1$s\",%2$s]", currency, pendingOnly);
  }

}

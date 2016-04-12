package org.knowm.xchange.btcchina.dto.account.request;

import org.knowm.xchange.btcchina.dto.BTCChinaRequest;

public class BTCChinaGetWithdrawalRequest extends BTCChinaRequest {

  private static final String METHOD_NAME = "getWithdrawal";

  /**
   * @param id the withdrawal ID.
   * @param currency [ BTC | LTC ].
   */
  public BTCChinaGetWithdrawalRequest(long id, String currency) {

    method = METHOD_NAME;
    params = String.format("[%1$d,\"%2$s\"]", id, currency);
  }

}

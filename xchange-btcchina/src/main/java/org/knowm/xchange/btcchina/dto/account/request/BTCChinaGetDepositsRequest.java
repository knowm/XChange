package org.knowm.xchange.btcchina.dto.account.request;

import org.knowm.xchange.btcchina.dto.BTCChinaRequest;

public class BTCChinaGetDepositsRequest extends BTCChinaRequest {

  private static final String METHOD_NAME = "getDeposits";

  public BTCChinaGetDepositsRequest(String currency, boolean pendingOnly) {

    method = METHOD_NAME;
    params = String.format("[\"%1$s\",%2$s]", currency, pendingOnly);
  }

}

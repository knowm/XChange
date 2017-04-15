package org.knowm.xchange.btcchina.dto.account.request;

import java.math.BigDecimal;

import org.knowm.xchange.btcchina.dto.BTCChinaRequest;

/**
 * @author David Yam
 */
public final class BTCChinaRequestWithdrawalRequest extends BTCChinaRequest {

  private static final String METHOD_NAME = "requestWithdrawal";

  /**
   * @param currency [ BTC | LTC ].
   * @param amount amount to withdraw.
   */
  public BTCChinaRequestWithdrawalRequest(String currency, BigDecimal amount) {

    method = METHOD_NAME;
    params = String.format("[\"%1$s\",%2$s]", currency, amount.toPlainString());
  }

  @Override
  public String toString() {

    return String.format("BTCChinaRequestWithdrawalRequest{id=%d, method=%s, params=%s}", id, method, params);
  }

}

package com.xeiam.xchange.btcchina.dto.account.request;

import java.math.BigDecimal;

import com.xeiam.xchange.btcchina.dto.BTCChinaRequest;
import com.xeiam.xchange.currency.Currencies;

/**
 * @author David Yam
 */
public final class BTCChinaRequestWithdrawalRequest extends BTCChinaRequest {

  private static final String METHOD_NAME = "requestWithdrawal";

  /**
   * Constructor
   * 
   * @deprecated Use {@link #BTCChinaRequestWithdrawalRequest(String, BigDecimal)} instead.
   */
  @Deprecated
  public BTCChinaRequestWithdrawalRequest(BigDecimal amount) {

    method = METHOD_NAME;
    params = "[\"" + Currencies.BTC + "\"," + amount.doubleValue() + "]";
  }

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

    return String.format("BTCChinaRequestWithdrawalRequest{id=%d, method=%s, params=%s}", id, method, params.toString());
  }

}

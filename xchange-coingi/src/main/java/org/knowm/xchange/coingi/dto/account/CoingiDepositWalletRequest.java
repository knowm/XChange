package org.knowm.xchange.coingi.dto.account;

import org.knowm.xchange.coingi.dto.CoingiAuthenticatedRequest;

public class CoingiDepositWalletRequest extends CoingiAuthenticatedRequest {
  private String currency;

  public String getCurrency() {
    return currency;
  }

  public CoingiDepositWalletRequest setCurrency(String currency) {
    this.currency = currency;
    return this;
  }
}

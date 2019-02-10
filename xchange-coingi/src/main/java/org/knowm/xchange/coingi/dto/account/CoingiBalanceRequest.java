package org.knowm.xchange.coingi.dto.account;

import org.knowm.xchange.coingi.dto.CoingiAuthenticatedRequest;

public class CoingiBalanceRequest extends CoingiAuthenticatedRequest {
  private String currencies;

  public String getCurrencies() {
    return currencies;
  }

  public CoingiBalanceRequest setCurrencies(String currencies) {
    this.currencies = currencies;
    return this;
  }
}

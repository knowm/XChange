package org.knowm.xchange.coingi.dto.request;

public class BalanceRequest extends AuthenticatedRequest {
  private String currencies;

  public String getCurrencies() {
    return currencies;
  }

  public BalanceRequest setCurrencies(String currencies) {
    this.currencies = currencies;
    return this;
  }
}

package org.knowm.xchange.gemini.v1.dto.trade;

import org.knowm.xchange.service.trade.params.CancelAllOrders;

public class GeminiCancelAllOrdersParams implements CancelAllOrders {
  private final String account;
  private final boolean sessionOnly;

  public GeminiCancelAllOrdersParams(String account, boolean sessionOnly) {
    this.account = account;
    this.sessionOnly = sessionOnly;
  }

  public String getAccount() {
    return account;
  }

  public boolean isSessionOnly() {
    return sessionOnly;
  }

  @Override
  public String toString() {
    return "GeminiCancellAllOrdersParams{" + "account='" + account + '\'' + '}';
  }
}

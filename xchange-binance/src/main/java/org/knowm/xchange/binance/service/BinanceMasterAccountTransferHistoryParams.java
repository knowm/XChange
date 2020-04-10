package org.knowm.xchange.binance.service;

import org.knowm.xchange.service.trade.params.DefaultTradeHistoryParamsTimeSpan;

public class BinanceMasterAccountTransferHistoryParams extends DefaultTradeHistoryParamsTimeSpan {

  private String email;

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}

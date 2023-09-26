package org.knowm.xchange.service.trade.params;

public class DefaultTradeHistoryParamUserReference implements TradeHistoryParamUserReference{

  private String userReference;

  public DefaultTradeHistoryParamUserReference(String userReference) {
    this.userReference = userReference;
  }

  @Override
  public String getUserReference() {
    return userReference;
  }

  @Override
  public void setUserReference(String userReference) {
    this.userReference = userReference;
  }
}

package org.knowm.xchange.service.trade.params;

public class DefaultTradeHistoryParamId implements TradeHistoryParamId {

  private String id;

  public DefaultTradeHistoryParamId(String id) {
    this.id = id;
  }

  @Override
  public String getId() {
    return id;
  }

  @Override
  public void setId(String id) {
    this.id = id;
  }

}

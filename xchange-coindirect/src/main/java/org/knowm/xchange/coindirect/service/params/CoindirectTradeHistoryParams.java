package org.knowm.xchange.coindirect.service.params;

import org.knowm.xchange.service.trade.params.TradeHistoryParamsIdSpan;

public class CoindirectTradeHistoryParams implements TradeHistoryParamsIdSpan {
  private String startId;
  private String endId;

  @Override
  public String getStartId() {
    return startId;
  }

  @Override
  public void setStartId(String startId) {
    this.startId = startId;
  }

  @Override
  public String getEndId() {
    return endId;
  }

  @Override
  public void setEndId(String endId) {
    this.endId = endId;
  }
}

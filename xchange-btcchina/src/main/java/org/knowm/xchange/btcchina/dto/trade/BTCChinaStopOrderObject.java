package org.knowm.xchange.btcchina.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BTCChinaStopOrderObject {

  private final BTCChinaStopOrder stopOrder;

  public BTCChinaStopOrderObject(@JsonProperty("stop_order") BTCChinaStopOrder stopOrder) {

    this.stopOrder = stopOrder;
  }

  public BTCChinaStopOrder getStopOrder() {

    return stopOrder;
  }

  @Override
  public String toString() {

    return String.format("BTCChinaStopOrderObject{stopOrder=%s}", stopOrder);
  }

}

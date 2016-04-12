package org.knowm.xchange.btcchina.dto.trade;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BTCChinaStopOrdersObject {

  private final BTCChinaStopOrder[] stopOrders;

  public BTCChinaStopOrdersObject(@JsonProperty("stop_orders") BTCChinaStopOrder[] stopOrders) {

    this.stopOrders = stopOrders;
  }

  public BTCChinaStopOrder[] getStopOrders() {

    return stopOrders;
  }

  @Override
  public String toString() {

    return "BTCChinaStopOrdersObject [stopOrders=" + Arrays.toString(stopOrders) + "]";
  }

}

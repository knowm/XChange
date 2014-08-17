package com.xeiam.xchange.btcchina.dto.trade;

import org.apache.commons.lang3.builder.ToStringBuilder;

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

    return ToStringBuilder.reflectionToString(this);
  }

}

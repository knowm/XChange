package org.knowm.xchange.btcchina.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BTCChinaOrderObject {

  private final BTCChinaOrder order;

  public BTCChinaOrderObject(@JsonProperty("order") BTCChinaOrder order) {

    this.order = order;
  }

  public BTCChinaOrder getOrder() {

    return order;
  }

  @Override
  public String toString() {

    return String.format("BTCChinaOrderObject{order=%s}", order);
  }

}

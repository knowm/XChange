package org.knowm.xchange.mexbt.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MeXBTOpenOrdersInfo {

  private final String ins;
  private final MeXBTOpenOrder[] openOrders;

  public MeXBTOpenOrdersInfo(@JsonProperty("ins") String ins, @JsonProperty("openOrders") MeXBTOpenOrder[] openOrders) {
    this.ins = ins;
    this.openOrders = openOrders;
  }

  public String getIns() {
    return ins;
  }

  public MeXBTOpenOrder[] getOpenOrders() {
    return openOrders;
  }

}

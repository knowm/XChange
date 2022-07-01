package org.knowm.xchange.bankera.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class BankeraOpenOrders {

  private final List<BankeraOrder> openOrders;

  public BankeraOpenOrders(@JsonProperty("orders") List<BankeraOrder> openOrders) {

    this.openOrders = openOrders;
  }

  public List<BankeraOrder> getOpenOrders() {
    return openOrders;
  }
}

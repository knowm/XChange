package org.knowm.xchange.cobinhood.dto.trading;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class CobinhoodOrders {
  private final List<CobinhoodOrder> orders;

  public CobinhoodOrders(@JsonProperty("orders") List<CobinhoodOrder> orders) {
    this.orders = orders;
  }

  public List<CobinhoodOrder> getOrders() {
    return orders;
  }
}

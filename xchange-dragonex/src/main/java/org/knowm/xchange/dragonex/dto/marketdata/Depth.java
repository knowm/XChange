package org.knowm.xchange.dragonex.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class Depth {

  private final List<Order> buys;
  private final List<Order> sells;

  public Depth(@JsonProperty("buys") List<Order> buys, @JsonProperty("sells") List<Order> sells) {
    this.buys = buys;
    this.sells = sells;
  }

  public List<Order> getBuys() {
    return buys;
  }

  public List<Order> getSells() {
    return sells;
  }

  @Override
  public String toString() {
    return "Depth ["
        + (buys != null ? "buys=" + buys + ", " : "")
        + (sells != null ? "sells=" + sells : "")
        + "]";
  }
}

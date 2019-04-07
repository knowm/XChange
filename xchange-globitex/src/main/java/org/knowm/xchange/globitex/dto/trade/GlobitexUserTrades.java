package org.knowm.xchange.globitex.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.List;

public class GlobitexUserTrades implements Serializable {

  @JsonProperty("trades")
  private final List<GlobitexUserTrade> userTrades;

  public GlobitexUserTrades(@JsonProperty("trades") List<GlobitexUserTrade> userTrades) {
    this.userTrades = userTrades;
  }

  public List<GlobitexUserTrade> getUserTrades() {
    return userTrades;
  }

  @Override
  public String toString() {
    return "GlobitexUserTrades{" + "userTrades=" + userTrades + '}';
  }
}

package org.knowm.xchange.campbx.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import org.knowm.xchange.campbx.dto.CampBXOrder;
import org.knowm.xchange.campbx.dto.CampBXResponse;

/** @author Matija Mazi */
public final class MyOpenOrders extends CampBXResponse {

  @JsonProperty("Buy")
  private List<CampBXOrder> buy = new ArrayList<>();

  @JsonProperty("Sell")
  private List<CampBXOrder> sell = new ArrayList<>();

  public List<CampBXOrder> getBuy() {

    return buy;
  }

  public void setBuy(List<CampBXOrder> Buy) {

    this.buy = Buy;
  }

  public List<CampBXOrder> getSell() {

    return sell;
  }

  public void setSell(List<CampBXOrder> Sell) {

    this.sell = Sell;
  }

  @Override
  public String toString() {

    return "MyOpenOrders [buy="
        + buy
        + ", sell="
        + sell
        + ", getSuccess()="
        + getSuccess()
        + ", getInfo()="
        + getInfo()
        + ", getError()="
        + getError()
        + "]";
  }
}

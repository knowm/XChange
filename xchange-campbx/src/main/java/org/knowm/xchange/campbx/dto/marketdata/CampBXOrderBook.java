package org.knowm.xchange.campbx.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.List;
import org.knowm.xchange.campbx.dto.CampBXResponse;

/** @author Matija Mazi */
public final class CampBXOrderBook extends CampBXResponse {

  @JsonProperty("Bids")
  private List<List<BigDecimal>> bids;

  @JsonProperty("Asks")
  private List<List<BigDecimal>> asks;

  public List<List<BigDecimal>> getBids() {

    return bids;
  }

  public void setBids(List<List<BigDecimal>> bids) {

    this.bids = bids;
  }

  public List<List<BigDecimal>> getAsks() {

    return asks;
  }

  public void setAsks(List<List<BigDecimal>> asks) {

    this.asks = asks;
  }

  @Override
  public String toString() {

    return "CampBXOrderBook [bids="
        + bids
        + ", asks="
        + asks
        + ", getSuccess()="
        + getSuccess()
        + ", getInfo()="
        + getInfo()
        + ", getError()="
        + getError()
        + "]";
  }
}

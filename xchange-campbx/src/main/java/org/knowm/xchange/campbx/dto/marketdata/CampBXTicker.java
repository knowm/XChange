package org.knowm.xchange.campbx.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import org.knowm.xchange.campbx.dto.CampBXResponse;

/** @author Matija Mazi */
public final class CampBXTicker extends CampBXResponse {

  @JsonProperty("Last Trade")
  private BigDecimal last;

  @JsonProperty("Best Bid")
  private BigDecimal bid;

  @JsonProperty("Best Ask")
  private BigDecimal ask;

  public BigDecimal getLast() {

    return last;
  }

  public void setLast(BigDecimal last) {

    this.last = last;
  }

  public BigDecimal getBid() {

    return bid;
  }

  public void setBid(BigDecimal bid) {

    this.bid = bid;
  }

  public BigDecimal getAsk() {

    return ask;
  }

  public void setAsk(BigDecimal ask) {

    this.ask = ask;
  }

  @Override
  public String toString() {

    return "CampBXTicker [last="
        + last
        + ", bid="
        + bid
        + ", ask="
        + ask
        + ", getSuccess()="
        + getSuccess()
        + ", getInfo()="
        + getInfo()
        + ", getError()="
        + getError()
        + "]";
  }
}

package org.knowm.xchange.bitbns.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class PdaxOrderBook {

  private List<Asks> asks;
  private List<Asks> bids;
  private long dataUpdateTime;
  private long now;

  public PdaxOrderBook(
      @JsonProperty("asks") List<Asks> asks,
      @JsonProperty("bids") List<Asks> bids,
      @JsonProperty("dataUpdateTime") long dataUpdateTime,
      @JsonProperty("now") long now) {
    this.asks = asks;
    this.bids = bids;
    this.dataUpdateTime = dataUpdateTime;
    this.now = now;
  }

  public List<Asks> getAsks() {
    return asks;
  }

  public void setAsks(List<Asks> asks) {
    this.asks = asks;
  }

  public List<Asks> getBids() {
    return bids;
  }

  public void setBids(List<Asks> bids) {
    this.bids = bids;
  }

  public long getDataUpdateTime() {
    return dataUpdateTime;
  }

  public void setDataUpdateTime(long dataUpdateTime) {
    this.dataUpdateTime = dataUpdateTime;
  }

  public long getNow() {
    return now;
  }

  public void setNow(long now) {
    this.now = now;
  }

  @Override
  public String toString() {
    return "PdaxOrderBook [asks="
        + asks
        + ", bids="
        + bids
        + ", dataUpdateTime="
        + dataUpdateTime
        + ", now="
        + now
        + "]";
  }
}

package org.knowm.xchange.kuna.dto;

import java.util.Date;
import java.util.List;

public class KunaDepthDto {

  private Date timestamp;

  private List<List<String>> asks;

  private List<List<String>> bids;

  public Date getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
  }

  public List<List<String>> getAsks() {
    return asks;
  }

  public void setAsks(List<List<String>> asks) {
    this.asks = asks;
  }

  public List<List<String>> getBids() {
    return bids;
  }

  public void setBids(List<List<String>> bids) {
    this.bids = bids;
  }
}

package org.knowm.xchange.cryptsy.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CryptsyMarketId {

  @JsonProperty("marketid")
  String marketid;

  public CryptsyMarketId(@JsonProperty("marketid") String marketid) {

    this.marketid = marketid;
  }

  public String getMarketid() {

    return marketid;
  }

  public void setMarketid(String marketid) {

    this.marketid = marketid;
  }

  @Override
  public String toString() {

    return "CryptsyMarketId [marketid=" + marketid + "]";
  }

}

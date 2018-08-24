package org.knowm.xchange.bittrex.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BittrexTradeId {

  private final String uuid;

  public BittrexTradeId(@JsonProperty("uuid") String uuid) {
    this.uuid = uuid;
  }

  @Override
  public String toString() {
    return "BittrexTradeId [uuid=" + getUuid() + "]";
  }

  public String getUuid() {
    return uuid;
  }
}

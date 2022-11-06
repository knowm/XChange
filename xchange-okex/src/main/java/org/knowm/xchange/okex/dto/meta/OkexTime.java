package org.knowm.xchange.okex.dto.meta;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OkexTime {

  @JsonProperty("ts")
  private long serverTime;

  public Long getServerTime() {
    return serverTime;
  }
}

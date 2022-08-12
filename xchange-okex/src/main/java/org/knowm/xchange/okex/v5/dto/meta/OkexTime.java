package org.knowm.xchange.okex.v5.dto.meta;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class OkexTime {

  @JsonProperty("ts")
  private long serverTime;

  public Long getServerTime() {
    return serverTime;
  }
}

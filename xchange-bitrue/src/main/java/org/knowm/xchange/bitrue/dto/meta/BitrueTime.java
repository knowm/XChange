package org.knowm.xchange.bitrue.dto.meta;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class BitrueTime {

  @JsonProperty private long serverTime;

  public Date getServerTime() {
    return new Date(serverTime);
  }
}

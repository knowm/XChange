package org.knowm.xchange.ccex.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CCEXUUIDResponse {

  private String uuid;

  public CCEXUUIDResponse(@JsonProperty("uuid") String uuid) {
    super();
    this.uuid = uuid;
  }

  public String getUuid() {
    return uuid;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  @Override
  public String toString() {
    return "CCEXUUIDResponse [uuid=" + uuid + "]";
  }
}

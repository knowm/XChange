package org.knowm.xchange.hitbtc.v2.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HitbtcInternalTransferResponse {

  public final String id;
  public final HitbtcError error;

  public HitbtcInternalTransferResponse(@JsonProperty("id") String id, @JsonProperty("error") HitbtcError error) {
    this.id = id;
    this.error = error;
  }

  @Override
  public String toString() {
    return "InternalTransferResponse{" +
        "id='" + id + '\'' +
        ", error=" + error +
        '}';
  }
}
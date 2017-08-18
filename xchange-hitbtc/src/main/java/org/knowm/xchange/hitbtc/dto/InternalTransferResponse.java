package org.knowm.xchange.hitbtc.dto;

import org.knowm.xchange.hitbtc.dto.general.HitbtcError;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InternalTransferResponse {

  public final String id;
  public final HitbtcError error;


  public InternalTransferResponse(@JsonProperty("id") String id, @JsonProperty("error") HitbtcError error) {
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

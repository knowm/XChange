package org.knowm.xchange.dsx.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DsxInternalTransferResponse {

  public final String id;
  public final DsxError error;

  public DsxInternalTransferResponse(
      @JsonProperty("id") String id, @JsonProperty("error") DsxError error) {
    this.id = id;
    this.error = error;
  }

  @Override
  public String toString() {
    return "InternalTransferResponse{" + "id='" + id + '\'' + ", error=" + error + '}';
  }
}

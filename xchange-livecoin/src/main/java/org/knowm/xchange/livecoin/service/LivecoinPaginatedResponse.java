package org.knowm.xchange.livecoin.service;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LivecoinPaginatedResponse extends LivecoinResponse<List<Map>> {
  public final int totalRows;
  public final int startRow;
  public final int endRow;

  public LivecoinPaginatedResponse(@JsonProperty("success") boolean success, @JsonProperty("errorCode") Integer errorCode, @JsonProperty("errorMessage") String errorMessage, @JsonProperty("response") List<Map> data,
      @JsonProperty("totalRows") int totalRows, @JsonProperty("startRow") int startRow, @JsonProperty("endRow") int endRow) {
    super(success, errorCode, errorMessage, data);
    this.totalRows = totalRows;
    this.startRow = startRow;
    this.endRow = endRow;
  }
}

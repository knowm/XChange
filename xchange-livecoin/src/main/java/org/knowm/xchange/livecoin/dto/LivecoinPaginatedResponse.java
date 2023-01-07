package org.knowm.xchange.livecoin.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class LivecoinPaginatedResponse<T> extends LivecoinBaseResponse {

  private final List<T> data;
  private final int totalRows;
  private final int startRow;
  private final int endRow;

  public LivecoinPaginatedResponse(
      @JsonProperty("success") Boolean success,
      @JsonProperty("data") List<T> data,
      @JsonProperty("totalRows") int totalRows,
      @JsonProperty("startRow") int startRow,
      @JsonProperty("endRow") int endRow) {
    super(success);
    this.data = data;
    this.totalRows = totalRows;
    this.startRow = startRow;
    this.endRow = endRow;
  }

  public List<T> getData() {
    return data;
  }

  public int getTotalRows() {
    return totalRows;
  }

  public int getStartRow() {
    return startRow;
  }

  public int getEndRow() {
    return endRow;
  }
}

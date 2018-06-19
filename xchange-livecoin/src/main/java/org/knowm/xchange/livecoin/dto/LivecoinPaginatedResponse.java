package org.knowm.xchange.livecoin.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;

public class LivecoinPaginatedResponse extends LivecoinBaseResponse {

  private final List<Map> data;
  private final int totalRows;
  private final int startRow;
  private final int endRow;

  public LivecoinPaginatedResponse(
      @JsonProperty("success") Boolean success,
      @JsonProperty("data") List<Map> data,
      @JsonProperty("totalRows") int totalRows,
      @JsonProperty("startRow") int startRow,
      @JsonProperty("endRow") int endRow) {
    super(success);
    this.data = data;
    this.totalRows = totalRows;
    this.startRow = startRow;
    this.endRow = endRow;
  }

  public List<Map> getData() {
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

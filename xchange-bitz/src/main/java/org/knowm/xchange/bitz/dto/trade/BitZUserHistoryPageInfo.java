package org.knowm.xchange.bitz.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BitZUserHistoryPageInfo {
  private final int limit;
  private final int offest;
  private final int currentPage;
  private final int pageSize;
  private final int totalCount;
  private final int pageCount;

  public BitZUserHistoryPageInfo(
      @JsonProperty("limit") int limit,
      @JsonProperty("offest") int offest,
      @JsonProperty("current_page") int current_page,
      @JsonProperty("page_size") int page_size,
      @JsonProperty("total_count") int total_count,
      @JsonProperty("page_count") int page_count) {
    this.limit = limit;
    this.offest = offest;
    this.currentPage = current_page;
    this.pageSize = page_size;
    this.totalCount = total_count;
    this.pageCount = page_count;
  }

  public int getLimit() {
    return limit;
  }

  public int getOffest() {
    return offest;
  }

  public int getCurrentPage() {
    return currentPage;
  }

  public int getPageSize() {
    return pageSize;
  }

  public int getTotalCount() {
    return totalCount;
  }

  public int getPageCount() {
    return pageCount;
  }
}

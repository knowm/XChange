package org.knowm.xchange.dvchain.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class DVChainTradesResponse {
  private List<DVChainTrade> data;
  private Integer total;
  private Integer pageCount;

  public DVChainTradesResponse(
      @JsonProperty("data") List<DVChainTrade> data,
      @JsonProperty("total") Integer total,
      @JsonProperty("pageCount") Integer pageCount) {
    this.data = data;
    this.pageCount = pageCount;
    this.total = total;
  }

  public Integer getPageCount() {
    return pageCount;
  }

  public List<DVChainTrade> getData() {
    return data;
  }

  public Integer getTotal() {
    return total;
  }
}

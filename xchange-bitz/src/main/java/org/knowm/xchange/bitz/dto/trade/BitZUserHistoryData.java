package org.knowm.xchange.bitz.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BitZUserHistoryData {

  private final BitZUserHistory[] history;
  private final BitZUserHistoryPageInfo pageInfo;

  public BitZUserHistoryData(
      @JsonProperty("data") BitZUserHistory[] history,
      @JsonProperty("pageInfo") BitZUserHistoryPageInfo pageInfo) {
    this.history = history;
    this.pageInfo = pageInfo;
  }

  public BitZUserHistory[] getHistory() {
    return history;
  }

  public BitZUserHistoryPageInfo getPageInfo() {
    return pageInfo;
  }
}

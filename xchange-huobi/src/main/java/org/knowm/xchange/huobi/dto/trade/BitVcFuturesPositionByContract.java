package org.knowm.xchange.huobi.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BitVcFuturesPositionByContract {

  private final BitVcFuturesPosition[] weekPositions;
  private final BitVcFuturesPosition[] nextWeekPositions;

  public BitVcFuturesPositionByContract(@JsonProperty(value = "week", required = false) final BitVcFuturesPosition[] weekPositions,
      @JsonProperty(value = "nextWeek", required = false) final BitVcFuturesPosition[] nextWeekPositions) {
    this.weekPositions = weekPositions;
    this.nextWeekPositions = nextWeekPositions;
  }

  public BitVcFuturesPosition[] getWeekPositions() {
    return weekPositions;
  }

  public BitVcFuturesPosition[] getNextWeekPositions() {
    return nextWeekPositions;
  }
}

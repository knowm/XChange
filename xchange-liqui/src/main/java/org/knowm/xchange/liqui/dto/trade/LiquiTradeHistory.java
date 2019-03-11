package org.knowm.xchange.liqui.dto.trade;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.Map;

public class LiquiTradeHistory {

  private final Map<Long, LiquiUserTrade> history;

  @JsonCreator
  public LiquiTradeHistory(final Map<Long, LiquiUserTrade> history) {
    this.history = history;
  }

  public Map<Long, LiquiUserTrade> getHistory() {
    return history;
  }

  @Override
  public String toString() {
    return "LiquiTradeHistory{" + "history=" + history + '}';
  }
}

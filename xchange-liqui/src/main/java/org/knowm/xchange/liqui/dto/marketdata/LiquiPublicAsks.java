package org.knowm.xchange.liqui.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.List;

public class LiquiPublicAsks {

  private final List<LiquiPublicAsk> asks;

  @JsonCreator
  public LiquiPublicAsks(final List<LiquiPublicAsk> asks) {
    this.asks = asks;
  }

  public List<LiquiPublicAsk> getAsks() {
    return asks;
  }

  @Override
  public String toString() {
    return "LiquiPublicAsks{" + "asks=" + asks + '}';
  }
}

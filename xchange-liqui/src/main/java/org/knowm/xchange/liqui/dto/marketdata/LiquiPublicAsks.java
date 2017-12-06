package org.knowm.xchange.liqui.dto.marketdata;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;

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
    return "LiquiPublicAsks{" +
        "asks=" + asks +
        '}';
  }
}

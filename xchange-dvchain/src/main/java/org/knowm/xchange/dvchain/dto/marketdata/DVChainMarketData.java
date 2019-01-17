package org.knowm.xchange.dvchain.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class DVChainMarketData {
  private final List<DVChainLevel> levels;
  private final Long expiresAt;

  public DVChainMarketData(
      @JsonProperty("levels") List<DVChainLevel> levels,
      @JsonProperty("expiresAt") Long expiresAt) {
    this.levels = levels;
    this.expiresAt = expiresAt;
  }

  public List<DVChainLevel> getLevels() {
    return levels;
  }

  public Long getExpiresAt() {
    return expiresAt;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("MarketData {levels=");
    builder.append(levels.toString());
    builder.append(", expiresAt=");
    builder.append(expiresAt);
    builder.append("}");
    return builder.toString();
  }
}

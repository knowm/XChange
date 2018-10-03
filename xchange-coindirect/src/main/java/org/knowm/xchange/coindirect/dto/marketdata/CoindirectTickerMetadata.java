package org.knowm.xchange.coindirect.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CoindirectTickerMetadata {
  public final String market;
  public final String history;
  public final String grouping;
  public final long limit;

  public CoindirectTickerMetadata(
      @JsonProperty("market") String market,
      @JsonProperty("history") String history,
      @JsonProperty("grouping") String grouping,
      @JsonProperty("limit") long limit) {
    this.market = market;
    this.history = history;
    this.grouping = grouping;
    this.limit = limit;
  }

  @Override
  public String toString() {
    return "CoindirectTickerMetadata{"
        + "market='"
        + market
        + '\''
        + ", history='"
        + history
        + '\''
        + ", grouping='"
        + grouping
        + '\''
        + ", limit="
        + limit
        + '}';
  }
}

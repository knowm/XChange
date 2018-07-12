package org.knowm.xchange.coindirect.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CoindirectTradesMetadata {
  public final String market;
  public final String history;
  public final long limit;

  public CoindirectTradesMetadata(
      @JsonProperty("market") String market,
      @JsonProperty("history") String history,
      @JsonProperty("limit") long limit) {
    this.market = market;
    this.history = history;
    this.limit = limit;
  }

  @Override
  public String toString() {
    return "CoindirectTradesMetadata{"
        + "market='"
        + market
        + '\''
        + ", history='"
        + history
        + '\''
        + ", limit="
        + limit
        + '}';
  }
}

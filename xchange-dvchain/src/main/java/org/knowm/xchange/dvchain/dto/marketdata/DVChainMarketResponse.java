package org.knowm.xchange.dvchain.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.Map;

public class DVChainMarketResponse {
  private final Map<String, DVChainMarketData> marketData;

  @JsonCreator
  public DVChainMarketResponse(Map<String, DVChainMarketData> marketData) {
    this.marketData = marketData;
  }

  public Map<String, DVChainMarketData> getMarketData() {
    return marketData;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("Response {");
    marketData.forEach(
        (symbol, data) -> {
          builder.append("Symbol=");
          builder.append(symbol);
          builder.append(", data=");
          builder.append(data);
        });
    builder.append("}");
    return builder.toString();
  }
}

package org.knowm.xchange.gdax.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GDAXIdResponse {
  private final String id;

  public GDAXIdResponse(@JsonProperty("id") String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("CoinbaseExIdResponse [id=");
    builder.append(id);
    builder.append("]");
    return builder.toString();
  }
}

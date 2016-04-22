package org.knowm.xchange.coinbaseex.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CoinbaseExIdResponse {
  private final String id;

  public CoinbaseExIdResponse(@JsonProperty("id") String id) {
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

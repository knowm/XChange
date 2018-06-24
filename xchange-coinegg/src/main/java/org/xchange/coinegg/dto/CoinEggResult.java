package org.xchange.coinegg.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CoinEggResult<V> {

  private final V data;
  private final boolean result;

  public CoinEggResult(@JsonProperty("result") boolean result, @JsonProperty("data") V data) {
    // TODO: Some Validation - See GatecoinResult.java
    this.result = result;
    this.data = data;
  }

  public V getData() {
    return data;
  }

  public boolean getResult() {
    return result;
  }
}

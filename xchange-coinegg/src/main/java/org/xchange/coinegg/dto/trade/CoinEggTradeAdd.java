package org.xchange.coinegg.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CoinEggTradeAdd {

  private final boolean result;
  private final int id;

  public CoinEggTradeAdd(@JsonProperty("result") boolean result, @JsonProperty("id") int id) {
    this.result = result;
    this.id = id;
  }

  public boolean getResult() {
    return result;
  }

  public int getID() {
    return id;
  }
}

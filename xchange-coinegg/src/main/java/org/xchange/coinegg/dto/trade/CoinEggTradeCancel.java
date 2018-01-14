package org.xchange.coinegg.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CoinEggTradeCancel {

  private final boolean result;
  private final int id;
  
  public CoinEggTradeCancel(@JsonProperty("result") boolean result, @JsonProperty("id") int id) {
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

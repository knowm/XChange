package info.bitrich.xchangestream.hitbtc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/** Created by Pavel Chertalev on 15.03.2018. */
public class HitbtcWebSocketBaseParams {

  protected final String symbol;

  public HitbtcWebSocketBaseParams(@JsonProperty("symbol") String symbol) {
    this.symbol = symbol;
  }

  public String getSymbol() {
    return symbol;
  }
}

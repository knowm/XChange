package info.bitrich.xchangestream.hitbtc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/** Created by Pavel Chertalev on 15.03.2018. */
public class HitbtcWebSocketTradesTransaction extends HitbtcWebSocketBaseTransaction {
  private final HitbtcWebSocketTradeParams params;

  public HitbtcWebSocketTradesTransaction(
      @JsonProperty("method") String method,
      @JsonProperty("params") HitbtcWebSocketTradeParams params) {
    super(method);
    this.params = params;
  }

  public HitbtcWebSocketTradeParams getParams() {
    return params;
  }
}

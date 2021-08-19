package info.bitrich.xchangestream.hitbtc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/** Created by Pavel Chertalev on 15.03.2018. */
public class HitbtcWebSocketSubscriptionMessage {

  private final String method;
  private final int id;
  private final HitbtcWebSocketBaseParams params;

  public HitbtcWebSocketSubscriptionMessage(
      @JsonProperty("id") int id,
      @JsonProperty("method") String method,
      @JsonProperty("params") HitbtcWebSocketBaseParams params) {
    this.id = id;
    this.method = method;
    this.params = params;
  }

  public String getMethod() {
    return method;
  }

  public int getId() {
    return id;
  }

  public HitbtcWebSocketBaseParams getParams() {
    return params;
  }
}

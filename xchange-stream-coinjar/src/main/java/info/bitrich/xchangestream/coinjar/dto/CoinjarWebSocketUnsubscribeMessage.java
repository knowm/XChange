package info.bitrich.xchangestream.coinjar.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CoinjarWebSocketUnsubscribeMessage {

  @JsonProperty("event")
  public final String event = "phx_leave";
}

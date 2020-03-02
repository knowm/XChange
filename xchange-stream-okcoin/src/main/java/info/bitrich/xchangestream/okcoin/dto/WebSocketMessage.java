package info.bitrich.xchangestream.okcoin.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WebSocketMessage {
  private final String event;
  private final String channel;

  public WebSocketMessage(
      @JsonProperty("event") String event, @JsonProperty("channel") String channel) {
    this.event = event;
    this.channel = channel;
  }

  public String getEvent() {
    return event;
  }

  public String getChannel() {
    return channel;
  }
}

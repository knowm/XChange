package info.bitrich.xchangestream.bitstamp.v2.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/** Created by Pavel Chertalev */
public class BitstampWebSocketSubscriptionMessage {

  private static final String EVENT = "event";
  private static final String DATA = "data";

  @JsonProperty(EVENT)
  private String event;

  @JsonProperty(DATA)
  private BitstampWebSocketData data;

  public BitstampWebSocketSubscriptionMessage() {}

  public BitstampWebSocketSubscriptionMessage(String event, BitstampWebSocketData data) {
    this.event = event;
    this.data = data;
  }

  public String getEvent() {
    return event;
  }

  public void setEvent(String event) {
    this.event = event;
  }

  public BitstampWebSocketData getData() {
    return data;
  }

  public void setData(BitstampWebSocketData data) {
    this.data = data;
  }
}

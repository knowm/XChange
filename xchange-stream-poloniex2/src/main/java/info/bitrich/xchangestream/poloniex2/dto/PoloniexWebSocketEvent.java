package info.bitrich.xchangestream.poloniex2.dto;

/** Created by Lukas Zaoralek on 11.11.17. */
public abstract class PoloniexWebSocketEvent {
  private String eventType;

  public PoloniexWebSocketEvent(String eventType) {
    this.eventType = eventType;
  }

  public String getEventType() {
    return eventType;
  }
}

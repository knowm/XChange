package info.bitrich.xchangestream.kraken.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import info.bitrich.xchangestream.kraken.dto.enums.KrakenEventType;

/** @author pchertalev */
public class KrakenEvent {

  @JsonProperty("event")
  private final KrakenEventType event;

  public KrakenEvent(KrakenEventType event) {
    this.event = event;
  }

  public KrakenEventType getEvent() {
    return event;
  }
}

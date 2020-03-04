package org.knowm.xchange.stream.kraken.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.stream.kraken.dto.enums.KrakenEventType;

/** @author pchertalev */
public class KrakenEvent {

  @JsonProperty("event")
  private final KrakenEventType event;

  @JsonProperty("error")
  private String error;

  public KrakenEvent(KrakenEventType event) {
    this.event = event;
  }

  public KrakenEventType getEvent() {
    return event;
  }

  public String getError() {
    return error;
  }
}

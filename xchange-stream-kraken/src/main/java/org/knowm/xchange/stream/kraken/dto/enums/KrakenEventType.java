package org.knowm.xchange.stream.kraken.dto.enums;

import java.util.Arrays;
import org.apache.commons.lang3.StringUtils;

public enum KrakenEventType {
  heartbeat,
  subscribe,
  unsubscribe,
  systemStatus,
  subscriptionStatus,
  ping,
  pong,
  error;

  public static KrakenEventType getEvent(String event) {
    return Arrays.stream(KrakenEventType.values())
        .filter(e -> StringUtils.equalsIgnoreCase(event, e.name()))
        .findFirst()
        .orElse(null);
  }
}

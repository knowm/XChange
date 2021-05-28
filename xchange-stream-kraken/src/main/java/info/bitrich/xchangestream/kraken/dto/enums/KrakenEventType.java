package info.bitrich.xchangestream.kraken.dto.enums;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

public enum KrakenEventType {
    heartbeat,
    subscribe,
    subscribed,
    unsubscribe,
    unsubscribed,
    systemStatus,
    subscriptionStatus,
    pingStatus,
    ping,
    pong,
    error,
    alert,
    info;

    public static KrakenEventType getEvent(String event) {
        return Arrays.stream(KrakenEventType.values())
                .filter(e -> StringUtils.equalsIgnoreCase(event, e.name()))
                .findFirst()
                .orElse(null);
    }
}

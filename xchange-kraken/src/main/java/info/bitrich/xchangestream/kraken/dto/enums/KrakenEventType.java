package info.bitrich.xchangestream.kraken.dto.enums;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

public enum KrakenEventType {
    heartbeat,
    subscribe,
    unsubscribe,
    systemStatus,
    subscriptionStatus,
    ping, pong;

    public static KrakenEventType getEvent(String event) {
        return Arrays.stream(KrakenEventType.values())
                .filter(e -> StringUtils.equalsIgnoreCase(event, e.name()))
                .findFirst()
                .orElse(null);
    }
}

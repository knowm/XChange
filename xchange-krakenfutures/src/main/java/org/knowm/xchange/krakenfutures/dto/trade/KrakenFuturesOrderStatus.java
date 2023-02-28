package org.knowm.xchange.krakenfutures.dto.trade;

public enum KrakenFuturesOrderStatus {
    ENTERED_BOOK,
    FULLY_EXECUTED,
    REJECTED,
    CANCELLED,
    TRIGGER_PLACED,
    TRIGGER_ACTIVATION_FAILURE,
    untouched,
    partiallyFilled
}

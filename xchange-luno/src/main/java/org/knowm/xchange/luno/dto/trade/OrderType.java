package org.knowm.xchange.luno.dto.trade;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum OrderType {
    ASK, BID    // <-- limit order types
    , SELL, BUY // <-- market order types
    , UNKNOWN;
    
    @JsonCreator
    public static OrderType create(String s) {
        try {
            return OrderType.valueOf(s);
        } catch (Exception e) {
            return UNKNOWN;
        }
    }
}
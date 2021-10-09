package org.knowm.xchange.binance.futures.dto.trade;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum NewOrderRespType {
    ACK,
    RESULT;

    @JsonCreator
    public static NewOrderRespType getNewOrderRespType(String s) {
        try {
            return NewOrderRespType.valueOf(s);
        } catch (Exception e) {
            throw new RuntimeException("Unknown NewOrderRespType " + s + ".");
        }
    }
}

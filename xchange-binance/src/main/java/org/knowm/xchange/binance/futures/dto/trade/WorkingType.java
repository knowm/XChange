package org.knowm.xchange.binance.futures.dto.trade;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum WorkingType {
    MARK_PRICE,
    CONTRACT_PRICE;

    @JsonCreator
    public static WorkingType getWorkingType(String s) {
        try {
            return WorkingType.valueOf(s);
        } catch (Exception e) {
            throw new RuntimeException("Unknown working type " + s + ".");
        }
    }
}

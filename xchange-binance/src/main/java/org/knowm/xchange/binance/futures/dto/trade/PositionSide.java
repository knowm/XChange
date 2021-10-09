package org.knowm.xchange.binance.futures.dto.trade;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum PositionSide {
    BOTH,
    LONG,
    SHORT;

    @JsonCreator
    public static PositionSide getPositionSide(String s) {
        try {
            return PositionSide.valueOf(s);
        } catch (Exception e) {
            throw new RuntimeException("Unknown position side " + s + ".");
        }
    }
}

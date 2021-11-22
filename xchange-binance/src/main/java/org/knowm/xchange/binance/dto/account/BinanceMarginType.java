package org.knowm.xchange.binance.dto.account;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum BinanceMarginType {
    ISOLATED,
    CROSSED;

    @JsonCreator
    public static BinanceMarginType getBinanceMarginType(String s) {
        try {
            return BinanceMarginType.valueOf(s);
        } catch (Exception e) {
            throw new RuntimeException("Unknown margin type " + s + ".");
        }
    }
}

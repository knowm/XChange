package org.knowm.xchange.binance.dto.account;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum BinanceMarginType {
    ISOLATED,
    CROSSED;

    @JsonCreator
    public static BinanceMarginType getBinanceMarginType(String s) {
        try {
            if (s.equalsIgnoreCase("isolated")) {
                return BinanceMarginType.ISOLATED;
            } else if (s.equalsIgnoreCase("cross")) {
                return BinanceMarginType.CROSSED;
            }
            return BinanceMarginType.valueOf(s.toUpperCase());
        } catch (Exception e) {
            throw new RuntimeException("Unknown margin type " + s + ".");
        }
    }
}

package org.knowm.xchange.binance.dto.account;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum BinanceMarginPositionSide {
    BOTH, LONG, SHORT;

    @JsonCreator
    public static BinanceMarginPositionSide getBinanceMarginPositionSide(String s) {
        try {
            return BinanceMarginPositionSide.valueOf(s);
        } catch (Exception e) {
            throw new RuntimeException("Unknown margin position type " + s + ".");
        }
    }
}

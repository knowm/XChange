package org.knowm.xchange.binance.dto.account;

public enum BinanceMarginPositionType {
    ADD(1), REDUCE(2);

    final int value;

    BinanceMarginPositionType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

package org.knowm.xchange.binance.dto.account;

public enum BinanceFutureTransferType {
    SPOT_TO_USDT_FUTURES(1),
    USDT_FUTURES_TO_SPOT(2),
    SPOT_TO_COIN_FUTURES(3),
    COIN_FUTURES_TO_SPOT(4);

    private final int value;

    BinanceFutureTransferType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

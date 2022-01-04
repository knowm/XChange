package org.knowm.xchange.binance.dto.account;

import org.junit.Test;

import static org.junit.Assert.*;

public class BinanceFutureTransferTypeTest {
    @Test
    public void testValues() {
        // Binance API Contract
        assertEquals(BinanceFutureTransferType.SPOT_TO_USDT_FUTURES.getValue(), 1);
        assertEquals(BinanceFutureTransferType.USDT_FUTURES_TO_SPOT.getValue(), 2);
        assertEquals(BinanceFutureTransferType.SPOT_TO_COIN_FUTURES.getValue(), 3);
        assertEquals(BinanceFutureTransferType.COIN_FUTURES_TO_SPOT.getValue(), 4);
    }
}
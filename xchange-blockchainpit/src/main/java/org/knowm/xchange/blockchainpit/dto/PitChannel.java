package org.knowm.xchange.blockchainpit.dto;

public interface PitChannel {
    String HEARTBEAT = "heartbeat";
    String PRICES = "prices";
    String TICKER = "ticker";
    String TRADES = "trades";
    String SYMBOLS = "symbols";
    String ORDERBOOK_L2 = "l2";
    String ORDERBOOK_L3 = "l3";
    String TRADING = "trading";
    String AUTH = "auth";
}

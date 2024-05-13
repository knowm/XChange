package org.knowm.xchange.coinbase.v2.service;

public enum TransactionType {
    BUY("buy"),
    SELL("sell"),
    RECEIVE("receive"),
    SEND("send");

    String name;

    TransactionType(String name) {
        this.name = name;
    }

    String getName() {
        return name;
    }
}
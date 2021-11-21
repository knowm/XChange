package org.knowm.xchange.binance.service.account.params;

import org.knowm.xchange.binance.dto.account.BinanceMarginPositionSide;
import org.knowm.xchange.binance.dto.account.BinanceMarginPositionType;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.service.account.params.AccountPositionMarginParams;

import java.math.BigDecimal;

public class BinanceAccountPositionMarginParams implements AccountPositionMarginParams {
    private final CurrencyPair pair;
    private final BinanceMarginPositionSide side;
    private final BigDecimal amount;
    private final BinanceMarginPositionType type;

    public BinanceAccountPositionMarginParams(CurrencyPair pair, BinanceMarginPositionSide side, BigDecimal amount, BinanceMarginPositionType type) {
        this.pair = pair;
        this.side = side;
        this.amount = amount;
        this.type = type;
    }

    public CurrencyPair getPair() {
        return pair;
    }

    public BinanceMarginPositionSide getSide() {
        return side;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BinanceMarginPositionType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "BinanceAccountPositionMarginParams{" +
                "pair=" + pair +
                ", side=" + side +
                ", amount=" + amount +
                ", type=" + type +
                '}';
    }
}

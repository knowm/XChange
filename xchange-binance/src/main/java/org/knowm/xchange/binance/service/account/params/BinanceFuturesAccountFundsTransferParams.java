package org.knowm.xchange.binance.service.account.params;

import org.knowm.xchange.binance.dto.account.BinanceFutureTransferType;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.service.account.params.AccountFundsTransferParams;

import java.math.BigDecimal;

public class BinanceFuturesAccountFundsTransferParams implements AccountFundsTransferParams {
    final private Currency currency;
    final private BigDecimal amount;
    final private BinanceFutureTransferType type;

    public BinanceFuturesAccountFundsTransferParams(Currency currency, BigDecimal amount, BinanceFutureTransferType type) {
        this.currency = currency;
        this.amount = amount;
        this.type = type;
    }

    @Override
    public Currency getCurrency() {
        return currency;
    }

    @Override
    public BigDecimal getAmount() {
        return amount;
    }

    public BinanceFutureTransferType getType() {
        return type;
    }
}

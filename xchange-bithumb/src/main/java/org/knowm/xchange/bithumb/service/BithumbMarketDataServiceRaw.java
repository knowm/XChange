package org.knowm.xchange.bithumb.service;

import org.knowm.xchange.bithumb.dto.marketdata.BithumbOrderbook;
import org.knowm.xchange.bithumb.dto.marketdata.BithumbTicker;
import org.knowm.xchange.bithumb.dto.marketdata.BithumbTickersReturn;
import org.knowm.xchange.bithumb.dto.marketdata.BithumbTransactionHistory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;

import java.util.List;

public class BithumbMarketDataServiceRaw {

    public BithumbTicker getBithumbTicker(CurrencyPair currencyPair) {
        throw new NotYetImplementedForExchangeException();
    }

    public BithumbTickersReturn getBithumbTickers() {
        throw new NotYetImplementedForExchangeException();
    }

    public BithumbOrderbook getBithumbOrderBook() {
        throw new NotYetImplementedForExchangeException();
    }

    public List<BithumbTransactionHistory> getBithumbTrades(CurrencyPair currencyPair) {
        throw new NotYetImplementedForExchangeException();
    }
}

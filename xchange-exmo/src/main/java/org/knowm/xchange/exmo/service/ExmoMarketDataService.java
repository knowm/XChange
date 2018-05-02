package org.knowm.xchange.exmo.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.marketdata.MarketDataService;

import java.io.IOException;
import java.util.Map;

public class ExmoMarketDataService extends ExmoMarketDataServiceRaw implements MarketDataService {
    protected ExmoMarketDataService(Exchange exchange) {
        super(exchange);
    }

    @Override
    public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {
        Map<CurrencyPair, Ticker> tickers = tickers();
        return tickers.get(currencyPair);
    }

    @Override
    public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {
        return orderBook(currencyPair);
    }
}

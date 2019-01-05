package org.knowm.xchange.bithumb.service;

import org.knowm.xchange.bithumb.BithumbAdapters;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.marketdata.params.Params;

import java.util.List;

public class BithumbMarketDataService extends BithumbMarketDataServiceRaw
        implements MarketDataService {

    @Override
    public Ticker getTicker(CurrencyPair currencyPair, Object... args) {
        return BithumbAdapters.adaptTicker(getBithumbTicker(currencyPair), currencyPair);
    }

    @Override
    public List<Ticker> getTickers(Params params) {
        return BithumbAdapters.adaptTickers(getBithumbTickers());
    }

    @Override
    public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) {
        return BithumbAdapters.adaptOrderBook(getBithumbOrderBook());
    }

    @Override
    public Trades getTrades(CurrencyPair currencyPair, Object... args) {
        return BithumbAdapters.adaptTrades(getBithumbTrades(currencyPair), currencyPair);
    }
}

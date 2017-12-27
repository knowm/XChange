package org.known.xchange.acx.service.marketdata;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.known.xchange.acx.AcxApi;
import org.known.xchange.acx.AcxMapper;
import org.known.xchange.acx.dto.AcxTrade;
import org.known.xchange.acx.dto.marketdata.AcxOrderBook;
import org.known.xchange.acx.dto.marketdata.AcxMarket;

import java.io.IOException;
import java.util.List;

public class AcxMarketDataService implements MarketDataService {

    private final AcxApi api;
    private final AcxMapper mapper;

    public AcxMarketDataService(AcxApi api, AcxMapper mapper) {
        this.api = api;
        this.mapper = mapper;
    }

    @Override
    public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {
        AcxMarket tickerData = api.getTicker(getAcxMarket(currencyPair));
        return mapper.mapTicker(currencyPair, tickerData);
    }

    @Override
    public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {
        AcxOrderBook orderBook = api.getOrderBook(getAcxMarket(currencyPair));
        return mapper.mapOrderBook(currencyPair, orderBook);
    }

    @Override
    public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {
        List<AcxTrade> trades = api.getTrades(getAcxMarket(currencyPair));
        return mapper.mapTrades(currencyPair, trades);
    }

    private static String getAcxMarket(CurrencyPair currencyPair) {
        return currencyPair.base.getCurrencyCode().toLowerCase() +
                currencyPair.counter.getCurrencyCode().toLowerCase();
    }
}

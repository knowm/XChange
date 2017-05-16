package org.knowm.xchange.btcc.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.btcc.BTCC;
import org.knowm.xchange.btcc.BTCCAdapters;
import org.knowm.xchange.btcc.dto.marketdata.BTCCTicker;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.marketdata.MarketDataService;

import java.io.IOException;
import java.util.Map;

public class BTCCMarketDataService extends BTCCBaseService<BTCC> implements MarketDataService {

    public BTCCMarketDataService(Exchange exchange) {
        super(exchange, BTCC.class);
    }

    @Override
    public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
        // Request data
        String symbol = currencyPair.base.getCurrencyCode() + currencyPair.counter.getCurrencyCode();
        BTCCTicker btccTicker = getBTCCTicker(symbol);

        // Adapt to XChange DTOs
        return btccTicker != null ? BTCCAdapters.adaptTicker(btccTicker, currencyPair) : null;
    }

    @Override
    public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
        throw new NotYetImplementedForExchangeException();
    }

    @Override
    public Trades getTrades(CurrencyPair currencyPair, Object... args) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
        throw new NotYetImplementedForExchangeException();
    }


    private BTCCTicker getBTCCTicker(String symbol) throws IOException {
        Map<String, BTCCTicker> response = btcc.getMarketTicker(symbol);
        return response.get("ticker");
    }

}

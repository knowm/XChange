package org.knowm.xchange.coindirect.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.coindirect.CoindirectAdapters;
import org.knowm.xchange.coindirect.dto.marketdata.CoindirectOrderbook;
import org.knowm.xchange.coindirect.dto.marketdata.CoindirectTicker;
import org.knowm.xchange.currency.CurrencyPair;

import java.io.IOException;

public class CoindirectMarketDataServiceRaw extends CoindirectBaseService {
    /**
     * Constructor
     *
     * @param exchange
     */
    protected CoindirectMarketDataServiceRaw(Exchange exchange) {
        super(exchange);
    }

    public CoindirectOrderbook getCoindirectOrderbook(CurrencyPair pair) throws IOException {
        return coindirect.getExchangeOrderBook(CoindirectAdapters.toSymbol(pair));
    }

    public CoindirectTicker getCoindirectTicker(CurrencyPair pair, String history) throws IOException {
        return coindirect.getHistoricalExchangeTrades(CoindirectAdapters.toSymbol(pair), history);
    }
}

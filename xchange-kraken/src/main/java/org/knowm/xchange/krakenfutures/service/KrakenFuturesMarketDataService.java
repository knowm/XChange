package org.knowm.xchange.krakenfutures.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.exceptions.ExchangeUnavailableException;
import org.knowm.xchange.kraken.KrakenAdapters;
import org.knowm.xchange.kraken.dto.marketdata.results.KrakenOrderBookResult;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.marketdata.params.Params;

import java.io.IOException;
import java.util.List;

public class KrakenFuturesMarketDataService extends KrakenFuturesBaseService implements MarketDataService {

    /**
     * Constructor
     *
     * @param exchange
     */

    public KrakenFuturesMarketDataService(Exchange exchange) {
        super(exchange);
    }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {
      throw new UnsupportedOperationException();
  }

    @Override
    public List<Ticker> getTickers(Params params) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {
        String currencyString = currencyPair.base.toString() + currencyPair.counter.toString();
        KrakenOrderBookResult krakenOrderBook = kraken.getOrderbook(currencyString);

        return KrakenAdapters.adaptFuturesOrderBook(krakenOrderBook, currencyPair);
    }

    @Override
    public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {
        throw new ExchangeUnavailableException();
    }
}

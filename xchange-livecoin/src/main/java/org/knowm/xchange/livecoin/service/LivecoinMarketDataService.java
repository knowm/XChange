package org.knowm.xchange.livecoin.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.livecoin.LivecoinAdapters;
import org.knowm.xchange.livecoin.dto.marketdata.LivecoinTicker;
import org.knowm.xchange.service.marketdata.MarketDataService;

import java.io.IOException;

public class LivecoinMarketDataService extends LivecoinMarketDataServiceRaw implements MarketDataService {

  public LivecoinMarketDataService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    LivecoinTicker ticker = getLivecoinTicker(currencyPair);
    return LivecoinAdapters.adaptTicker(ticker, currencyPair);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {
    int depth = 50;
    if (args != null && args.length > 0) {
      if (args[0] instanceof Number) {
        Number arg = (Number) args[0];
        depth = arg.intValue();
      }
    }

    return LivecoinAdapters.adaptOrderBook(getOrderBookRaw(currencyPair, depth), currencyPair);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {
    return LivecoinAdapters.adaptTrades(getTrades(currencyPair), currencyPair);
  }

}

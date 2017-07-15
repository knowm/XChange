package org.knowm.xchange.yobit.service;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.yobit.YoBitAdapters;
import org.knowm.xchange.yobit.dto.marketdata.YoBitTicker;
import org.knowm.xchange.yobit.dto.marketdata.YoBitTickerReturn;

public class YoBitMarketDataService extends YoBitMarketDataServiceRaw implements MarketDataService {

  public YoBitMarketDataService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args)
      throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    YoBitTickerReturn ticker = getYoBitTicker(currencyPair);
    return YoBitAdapters.adaptTicker(ticker, currencyPair);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {

    long level = 50;
    if (args != null && args.length > 0) {
      if (args[0] instanceof Number) {
        Number arg = (Number) args[0];
        level = arg.intValue();
      }
    }

    return YoBitAdapters.adaptOrderBook(getOrderBookA(currencyPair, level), currencyPair);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {
    return YoBitAdapters.adaptTrades(getTrades(currencyPair), currencyPair);
  }
}
